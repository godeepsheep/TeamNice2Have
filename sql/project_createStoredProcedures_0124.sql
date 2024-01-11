use eamvstudie23_dk_db_project

DROP PROCEDURE IF EXISTS getMatches;
DROP PROCEDURE IF EXISTS getLeague;
DROP PROCEDURE IF EXISTS createMatch;
GO

CREATE PROCEDURE getMatches  
AS  
BEGIN  
	SELECT STRING_AGG(Name, '  - vs - ') AS Teams, STRING_AGG(Goals, ' - ') AS Goals FROM [Match]  
	LEFT JOIN TeamMatch ON [Match].ID = TeamMatch.MatchID  
	LEFT JOIN Team ON TeamMatch.TeamID = Team.ID  
	GROUP BY [Match].ID  
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