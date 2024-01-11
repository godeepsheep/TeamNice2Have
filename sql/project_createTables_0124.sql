use eamvstudie23_dk_db_project

DROP TABLE IF EXISTS [Event];
DROP TABLE IF EXISTS TeamMatch;
DROP TABLE IF EXISTS Team;
DROP TABLE IF EXISTS League;
DROP TABLE IF EXISTS [Match];
DROP TABLE IF EXISTS EventType;


/*Create tables*/

CREATE TABLE League (
    ID INT PRIMARY KEY IDENTITY(1,1),
    [Name] NVARCHAR(50) NOT NULL
);

CREATE TABLE [Match] (
    ID INT PRIMARY KEY IDENTITY(1,1),
    TimeStart DATETIME DEFAULT GETDATE(),
    TimeEnd DATETIME
);

CREATE TABLE Team (
    ID INT PRIMARY KEY IDENTITY(1,1),
    [Name] NVARCHAR(50) NOT NULL,
    LeagueID INT NOT NULL,

	CONSTRAINT FK_LeagueID 
	FOREIGN KEY (LeagueID)
    REFERENCES League(ID)
	ON DELETE NO ACTION
    ON UPDATE CASCADE
);

CREATE TABLE TeamMatch (
    MatchID INT NOT NULL,
    TeamID INT NOT NULL,
    Goals INT DEFAULT 0,
    Points INT DEFAULT 0,

	CONSTRAINT FK_MatchID 
	FOREIGN KEY (MatchID)
    REFERENCES [Match](ID)
	ON DELETE CASCADE
    ON UPDATE CASCADE,

	CONSTRAINT FK_TeamID 
	FOREIGN KEY (TeamID)
    REFERENCES Team(ID)
	ON DELETE CASCADE
    ON UPDATE CASCADE
);

create table EventType (
    ID INT PRIMARY KEY IDENTITY(1,1),
    [Name] NVARCHAR(50) NOT NULL
);

CREATE TABLE [Event] (
    ID INT PRIMARY KEY IDENTITY(1,1),
    TypeID INT NOT NULL,
    MatchID INT NOT NULL,
	TeamID INT NOT NULL,
	[Time] TIME,
	RealTime TIME,

	Constraint FK_TypeID 
	Foreign key (TypeID)
	References EventType(ID)
	ON DELETE CASCADE
    ON UPDATE CASCADE,

	Constraint FK_Event_MatchID 
	Foreign key (MatchID)
	References [Match](ID)
	ON DELETE CASCADE
    ON UPDATE CASCADE,

	Constraint FK_Event_TeamID 
	Foreign key (TeamID)
	References Team(ID)
	ON DELETE CASCADE
    ON UPDATE CASCADE
);

GO


/*Data insert*/
INSERT INTO League (name) VALUES ('Kvindeligaen');


INSERT INTO Team (name, LeagueID) VALUES ('Esbjerg', 1);
INSERT INTO Team (name, LeagueID) VALUES ('Ikast Håndbold', 1);
INSERT INTO Team (name, LeagueID) VALUES ('Odense Håndbold', 1);
INSERT INTO Team (name, LeagueID) VALUES ('Nykøbing F. Håndbold', 1);
INSERT INTO Team (name, LeagueID) VALUES ('Viborg HK', 1);
INSERT INTO Team (name, LeagueID) VALUES ('SønderjyskE Kvinder', 1);
INSERT INTO Team (name, LeagueID) VALUES ('Skanderborg Håndbold', 1);
INSERT INTO Team (name, LeagueID) VALUES ('København Håndbold', 1);
INSERT INTO Team (name, LeagueID) VALUES ('Ringkøbing Håndbold', 1);
INSERT INTO Team (name, LeagueID) VALUES ('Aarhus United', 1);
INSERT INTO Team (name, LeagueID) VALUES ('Silkeborg-Voel KFUM', 1);
INSERT INTO Team (name, LeagueID) VALUES ('Horsens Håndbold Elite', 1);
INSERT INTO Team (name, LeagueID) VALUES ('Bjerringbro FH', 1);
INSERT INTO Team (name, LeagueID) VALUES ('Ajax København', 1);



