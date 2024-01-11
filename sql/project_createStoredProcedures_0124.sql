use eamvstudie23_dk_db_project

DROP PROCEDURE IF EXISTS getMatches;
DROP PROCEDURE IF EXISTS getLeague;
DROP PROCEDURE IF EXISTS createMatch;
GO

CREATE PROCEDURE getMatches  
AS  
BEGIN  
	WITH tempResult AS (	
		SELECT 
			ROW_NUMBER() OVER (PARTITION BY [Match].ID ORDER BY TeamMatch.Goals DESC) AS RowNo, 
			[Match].ID, 
			[Match].TimeStart AS [Date], 
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

	SELECT ID, [Date], Team1, Goals1, Team2, Goals2 FROM tempResult WHERE RowNo = 1;
END;
GO

CREATE PROCEDURE getLeague
AS
BEGIN
	SELECT 
		ROW_NUMBER() OVER (ORDER BY (SELECT NULL)) AS Standing, 
		Team.Name, 
		Count(Points.MatchID) AS Matches, 
		SUM(GoalsDiff) AS GoalsDiff,
		SUM(Points) AS Points
	FROM Team

	LEFT JOIN Points ON Team.ID = Points.TeamID

	GROUP BY Team.Name
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