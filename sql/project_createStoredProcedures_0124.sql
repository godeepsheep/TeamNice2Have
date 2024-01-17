use eamvstudie23_dk_db_project

DROP PROCEDURE IF EXISTS getMatches;
DROP PROCEDURE IF EXISTS getLeague;
DROP PROCEDURE IF EXISTS createMatch;
DROP PROCEDURE IF EXISTS setEvent;
DROP PROCEDURE IF EXISTS deleteEvent;
DROP PROCEDURE IF EXISTS getEvents;
DROP PROCEDURE IF EXISTS addTeam;
GO


CREATE PROCEDURE getMatches  
AS  
BEGIN  
	WITH tempResult AS (	
		SELECT 
			ROW_NUMBER() OVER (PARTITION BY [Match].ID ORDER BY TeamMatch.Goals DESC) AS RowNo, 
			[Match].ID, 
			FORMAT(TimeStart, 'dd.MM. HH:mm') AS [Time], 
			Team.Name AS Team1, 
			TeamMatch.Goals AS Goals1, 
			Team2.Name AS Team2, 
			TeamMatch2.Goals AS Goals2 
		FROM [Match]  

		LEFT JOIN TeamMatch ON [Match].ID = TeamMatch.MatchID  
		LEFT JOIN Team ON TeamMatch.TeamID = Team.ID  

		LEFT JOIN TeamMatch AS TeamMatch2 ON [Match].ID = TeamMatch2.MatchID AND TeamMatch.TeamID <> TeamMatch2.TeamID
		LEFT JOIN Team AS Team2 ON TeamMatch2.TeamID = Team2.ID
	)

	SELECT ID, [Time], Team1, Goals1, Team2, Goals2 FROM tempResult WHERE RowNo = 1 ORDER BY [Time] DESC;
END;
GO

CREATE PROCEDURE getLeague
AS
BEGIN
	SELECT 
		ROW_NUMBER() OVER (ORDER BY (SELECT NULL)) AS Standing, 
		Team.ID,
		Team.Name, 
		Count(Points.MatchID) AS Matches, 
		SUM(ISNULL(GoalsDiff,0)) AS GoalsDiff,
		SUM(ISNULL(Points,0)) AS Points
	FROM Team
	LEFT JOIN Points ON Team.ID = Points.TeamID

	GROUP BY Team.ID, Team.Name
	ORDER BY Points DESC, GoalsDiff DESC, Matches ASC;
END;
GO

CREATE PROCEDURE createMatch
	@t1 INT,
	@t2 INT
AS
BEGIN
	DECLARE @ID INT;
	INSERT INTO [Match] (TimeStart) OUTPUT INSERTED.ID VALUES (GETDATE());

	SET @ID = SCOPE_IDENTITY();
	INSERT INTO TeamMatch (MatchID, TeamID) VALUES (@ID, @t1);
	INSERT INTO TeamMatch (MatchID, TeamID) VALUES (@ID, @t2);
END;
GO

CREATE PROCEDURE setEvent	
	@type INT,
	@matchID INT,
	@teamID INT,
	@time TIME
AS
BEGIN
	IF @type = 1 BEGIN 
		UPDATE TeamMatch SET Goals = Goals+1 WHERE MatchID = @matchID AND TeamID = @teamID;
	END

	DECLARE @realTime TIME = CONVERT(TIME, GETDATE());
	INSERT INTO [Event] VALUES (@type, @matchID, @teamID, @time, @realTime);
END;
GO

CREATE PROCEDURE deleteEvent	
	@type INT,
	@matchID INT,
	@teamID INT
AS
BEGIN
	IF @type = 1 BEGIN 
		UPDATE TeamMatch SET Goals = Goals-1 WHERE MatchID = @matchID AND TeamID = @teamID;
	END

	DECLARE @ID INT;
	SET @ID = (SELECT TOP 1 ID FROM [Event] 
					WHERE MatchID = @matchID AND TeamID = @teamID AND TypeID = @type
					ORDER BY RealTime DESC
				);
	DELETE FROM [Event] WHERE ID = @ID;
END;
GO

CREATE PROCEDURE getEvents	
	@matchID INT
AS
BEGIN
	SELECT 
		TypeID AS Event_ID,
		EventType.Name AS [Event], 
		Team.ID AS Team_ID,
		Team.Name AS Team, 
		FORMAT(CONVERT(DATETIME, [Time], 121), 'mm:ss') AS [Time], 
		FORMAT(CONVERT(DATETIME, RealTime, 121), 'HH:mm:ss') AS RealTime
	FROM [Event]

	LEFT JOIN EventType ON TypeID = EventType.ID
	LEFT JOIN Team ON TeamID = Team.ID

	WHERE @matchID = 0 OR MatchID = @matchID
	ORDER BY RealTime DESC;
END;
GO

CREATE PROCEDURE addTeam	
	@teamName NVARCHAR (50),
	@league INT
AS
BEGIN
	DECLARE @ID INT;
	INSERT INTO Team (Name, LeagueID) VALUES (@teamName,@league);
	SET @ID = (SELECT SCOPE_IDENTITY());

    DECLARE @tmpTable TABLE (
        Standing INT,
		ID INT,
        Name NVARCHAR(255),
		Matches INT,
		GoalsDiff INT,
		Points INT
    );

	INSERT INTO @tmpTable
	exec getLeague;

	SELECT * FROM @tmpTable WHERE ID = @ID;
END;


	