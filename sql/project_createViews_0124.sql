use eamvstudie23_dk_db_project

DROP VIEW IF EXISTS Points;
GO

CREATE VIEW Points AS
	SELECT 
		TeamMatch.*, 
		MG.Goals AS Goals2, 
		TeamMatch.Goals-MG.Goals AS GoalsDiff,
	CASE
		WHEN MG.Goals >TeamMatch.Goals THEN 0
		WHEN MG.Goals <TeamMatch.Goals THEN 2
		ELSE 1
	END AS Points
FROM [Match]

LEFT JOIN TeamMatch ON [Match].ID = TeamMatch.MatchID
LEFT JOIN TeamMatch AS MG ON [Match].ID = MG.MatchID AND TeamMatch.TeamID <> MG.TeamID