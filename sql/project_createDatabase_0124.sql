USE [master]
GO
/****** Object:  Database [eamvstudie23_dk_db_project]    Script Date: 16-01-2024 19:02:52 ******/
CREATE DATABASE [eamvstudie23_dk_db_project]
 CONTAINMENT = NONE
 ON  PRIMARY 
( NAME = N'eamvstudie23_dk_db_project', FILENAME = N'D:\\SQL\eamvstudie23_dk_db_project.mdf' , SIZE = 51200KB , MAXSIZE = 15728640KB , FILEGROWTH = 51200KB )
 LOG ON 
( NAME = N'eamvstudie23_dk_db_project_log', FILENAME = N'D:\\SQL\eamvstudie23_dk_db_project_log.ldf' , SIZE = 51200KB , MAXSIZE = 2097152KB , FILEGROWTH = 51200KB )
 WITH CATALOG_COLLATION = DATABASE_DEFAULT
GO
ALTER DATABASE [eamvstudie23_dk_db_project] SET COMPATIBILITY_LEVEL = 120
GO
IF (1 = FULLTEXTSERVICEPROPERTY('IsFullTextInstalled'))
begin
EXEC [eamvstudie23_dk_db_project].[dbo].[sp_fulltext_database] @action = 'enable'
end
GO
ALTER DATABASE [eamvstudie23_dk_db_project] SET ANSI_NULL_DEFAULT OFF 
GO
ALTER DATABASE [eamvstudie23_dk_db_project] SET ANSI_NULLS OFF 
GO
ALTER DATABASE [eamvstudie23_dk_db_project] SET ANSI_PADDING OFF 
GO
ALTER DATABASE [eamvstudie23_dk_db_project] SET ANSI_WARNINGS OFF 
GO
ALTER DATABASE [eamvstudie23_dk_db_project] SET ARITHABORT OFF 
GO
ALTER DATABASE [eamvstudie23_dk_db_project] SET AUTO_CLOSE OFF 
GO
ALTER DATABASE [eamvstudie23_dk_db_project] SET AUTO_SHRINK OFF 
GO
ALTER DATABASE [eamvstudie23_dk_db_project] SET AUTO_UPDATE_STATISTICS ON 
GO
ALTER DATABASE [eamvstudie23_dk_db_project] SET CURSOR_CLOSE_ON_COMMIT OFF 
GO
ALTER DATABASE [eamvstudie23_dk_db_project] SET CURSOR_DEFAULT  GLOBAL 
GO
ALTER DATABASE [eamvstudie23_dk_db_project] SET CONCAT_NULL_YIELDS_NULL OFF 
GO
ALTER DATABASE [eamvstudie23_dk_db_project] SET NUMERIC_ROUNDABORT OFF 
GO
ALTER DATABASE [eamvstudie23_dk_db_project] SET QUOTED_IDENTIFIER OFF 
GO
ALTER DATABASE [eamvstudie23_dk_db_project] SET RECURSIVE_TRIGGERS OFF 
GO
ALTER DATABASE [eamvstudie23_dk_db_project] SET  DISABLE_BROKER 
GO
ALTER DATABASE [eamvstudie23_dk_db_project] SET AUTO_UPDATE_STATISTICS_ASYNC OFF 
GO
ALTER DATABASE [eamvstudie23_dk_db_project] SET DATE_CORRELATION_OPTIMIZATION OFF 
GO
ALTER DATABASE [eamvstudie23_dk_db_project] SET TRUSTWORTHY OFF 
GO
ALTER DATABASE [eamvstudie23_dk_db_project] SET ALLOW_SNAPSHOT_ISOLATION OFF 
GO
ALTER DATABASE [eamvstudie23_dk_db_project] SET PARAMETERIZATION SIMPLE 
GO
ALTER DATABASE [eamvstudie23_dk_db_project] SET READ_COMMITTED_SNAPSHOT OFF 
GO
ALTER DATABASE [eamvstudie23_dk_db_project] SET HONOR_BROKER_PRIORITY OFF 
GO
ALTER DATABASE [eamvstudie23_dk_db_project] SET RECOVERY SIMPLE 
GO
ALTER DATABASE [eamvstudie23_dk_db_project] SET  MULTI_USER 
GO
ALTER DATABASE [eamvstudie23_dk_db_project] SET PAGE_VERIFY CHECKSUM  
GO
ALTER DATABASE [eamvstudie23_dk_db_project] SET DB_CHAINING OFF 
GO
ALTER DATABASE [eamvstudie23_dk_db_project] SET FILESTREAM( NON_TRANSACTED_ACCESS = OFF ) 
GO
ALTER DATABASE [eamvstudie23_dk_db_project] SET TARGET_RECOVERY_TIME = 0 SECONDS 
GO
ALTER DATABASE [eamvstudie23_dk_db_project] SET DELAYED_DURABILITY = DISABLED 
GO
ALTER DATABASE [eamvstudie23_dk_db_project] SET ACCELERATED_DATABASE_RECOVERY = OFF  
GO
ALTER DATABASE [eamvstudie23_dk_db_project] SET QUERY_STORE = OFF
GO
USE [eamvstudie23_dk_db_project]
GO
/****** Object:  Table [dbo].[Match]    Script Date: 16-01-2024 19:02:52 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Match](
	[ID] [int] IDENTITY(1,1) NOT NULL,
	[TimeStart] [datetime] NULL,
	[TimeEnd] [datetime] NULL,
PRIMARY KEY CLUSTERED 
(
	[ID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, FILLFACTOR = 90, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[TeamMatch]    Script Date: 16-01-2024 19:02:52 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[TeamMatch](
	[MatchID] [int] NOT NULL,
	[TeamID] [int] NOT NULL,
	[Goals] [int] NULL
) ON [PRIMARY]
GO
/****** Object:  View [dbo].[Points]    Script Date: 16-01-2024 19:02:52 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO

CREATE VIEW [dbo].[Points] AS
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
GO
/****** Object:  Table [dbo].[Event]    Script Date: 16-01-2024 19:02:52 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Event](
	[ID] [int] IDENTITY(1,1) NOT NULL,
	[TypeID] [int] NOT NULL,
	[MatchID] [int] NOT NULL,
	[TeamID] [int] NOT NULL,
	[Time] [time](7) NULL,
	[RealTime] [time](7) NULL,
PRIMARY KEY CLUSTERED 
(
	[ID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, FILLFACTOR = 90, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[EventType]    Script Date: 16-01-2024 19:02:52 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[EventType](
	[ID] [int] IDENTITY(1,1) NOT NULL,
	[Name] [nvarchar](50) NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[ID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, FILLFACTOR = 90, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[League]    Script Date: 16-01-2024 19:02:52 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[League](
	[ID] [int] IDENTITY(1,1) NOT NULL,
	[Name] [nvarchar](25) NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[ID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, FILLFACTOR = 90, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Team]    Script Date: 16-01-2024 19:02:52 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Team](
	[ID] [int] IDENTITY(1,1) NOT NULL,
	[Name] [nvarchar](50) NOT NULL,
	[LeagueID] [int] NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[ID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, FILLFACTOR = 90, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
ALTER TABLE [dbo].[Match] ADD  DEFAULT (getdate()) FOR [TimeStart]
GO
ALTER TABLE [dbo].[Match] ADD  DEFAULT (dateadd(minute,(1),getdate())) FOR [TimeEnd]
GO
ALTER TABLE [dbo].[TeamMatch] ADD  DEFAULT ((0)) FOR [Goals]
GO
ALTER TABLE [dbo].[Event]  WITH CHECK ADD  CONSTRAINT [FK_Event_MatchID] FOREIGN KEY([MatchID])
REFERENCES [dbo].[Match] ([ID])
ON UPDATE CASCADE
ON DELETE CASCADE
GO
ALTER TABLE [dbo].[Event] CHECK CONSTRAINT [FK_Event_MatchID]
GO
ALTER TABLE [dbo].[Event]  WITH CHECK ADD  CONSTRAINT [FK_Event_TeamID] FOREIGN KEY([TeamID])
REFERENCES [dbo].[Team] ([ID])
ON UPDATE CASCADE
ON DELETE CASCADE
GO
ALTER TABLE [dbo].[Event] CHECK CONSTRAINT [FK_Event_TeamID]
GO
ALTER TABLE [dbo].[Event]  WITH CHECK ADD  CONSTRAINT [FK_TypeID] FOREIGN KEY([TypeID])
REFERENCES [dbo].[EventType] ([ID])
ON UPDATE CASCADE
ON DELETE CASCADE
GO
ALTER TABLE [dbo].[Event] CHECK CONSTRAINT [FK_TypeID]
GO
ALTER TABLE [dbo].[Team]  WITH CHECK ADD  CONSTRAINT [FK_LeagueID] FOREIGN KEY([LeagueID])
REFERENCES [dbo].[League] ([ID])
ON UPDATE CASCADE
GO
ALTER TABLE [dbo].[Team] CHECK CONSTRAINT [FK_LeagueID]
GO
ALTER TABLE [dbo].[TeamMatch]  WITH CHECK ADD  CONSTRAINT [FK_MatchID] FOREIGN KEY([MatchID])
REFERENCES [dbo].[Match] ([ID])
ON UPDATE CASCADE
ON DELETE CASCADE
GO
ALTER TABLE [dbo].[TeamMatch] CHECK CONSTRAINT [FK_MatchID]
GO
ALTER TABLE [dbo].[TeamMatch]  WITH CHECK ADD  CONSTRAINT [FK_TeamID] FOREIGN KEY([TeamID])
REFERENCES [dbo].[Team] ([ID])
ON UPDATE CASCADE
ON DELETE CASCADE
GO
ALTER TABLE [dbo].[TeamMatch] CHECK CONSTRAINT [FK_TeamID]
GO
/****** Object:  StoredProcedure [dbo].[addTeam]    Script Date: 16-01-2024 19:02:52 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE [dbo].[addTeam]	
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
GO
/****** Object:  StoredProcedure [dbo].[createMatch]    Script Date: 16-01-2024 19:02:52 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE [dbo].[createMatch]
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
/****** Object:  StoredProcedure [dbo].[deleteEvent]    Script Date: 16-01-2024 19:02:52 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE [dbo].[deleteEvent]	
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
/****** Object:  StoredProcedure [dbo].[getEvents]    Script Date: 16-01-2024 19:02:52 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE [dbo].[getEvents]	
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
/****** Object:  StoredProcedure [dbo].[getLeague]    Script Date: 16-01-2024 19:02:52 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE [dbo].[getLeague]
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
/****** Object:  StoredProcedure [dbo].[getMatches]    Script Date: 16-01-2024 19:02:52 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE [dbo].[getMatches]  
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
/****** Object:  StoredProcedure [dbo].[setEvent]    Script Date: 16-01-2024 19:02:52 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE [dbo].[setEvent]	
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
USE [master]
GO
ALTER DATABASE [eamvstudie23_dk_db_project] SET  READ_WRITE 
GO
