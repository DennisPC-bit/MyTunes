/*
 Navicat Premium Data Transfer

 Source Server         : DP DB
 Source Server Type    : SQL Server
 Source Server Version : 11007493
 Source Host           : 10.176.111.31:1433
 Source Catalog        : mytunesDennisCarloChristian
 Source Schema         : dbo

 Target Server Type    : SQL Server
 Target Server Version : 11007493
 File Encoding         : 65001

 Date: 11/12/2020 12:07:24
*/


-- ----------------------------
-- Table structure for category
-- ----------------------------
IF EXISTS (SELECT * FROM sys.all_objects WHERE object_id = OBJECT_ID(N'[dbo].[category]') AND type IN ('U'))
	DROP TABLE [dbo].[category]
GO

CREATE TABLE [dbo].[category] (
  [category_id] int  IDENTITY(1,1) NOT NULL,
  [category_name] nvarchar(255) COLLATE Danish_Norwegian_CI_AS  NULL
)
GO

ALTER TABLE [dbo].[category] SET (LOCK_ESCALATION = TABLE)
GO


-- ----------------------------
-- Table structure for playlist
-- ----------------------------
IF EXISTS (SELECT * FROM sys.all_objects WHERE object_id = OBJECT_ID(N'[dbo].[playlist]') AND type IN ('U'))
	DROP TABLE [dbo].[playlist]
GO

CREATE TABLE [dbo].[playlist] (
  [playlist_id] int  IDENTITY(1,1) NOT NULL,
  [playlist_name] nvarchar(255) COLLATE Danish_Norwegian_CI_AS  NOT NULL
)
GO

ALTER TABLE [dbo].[playlist] SET (LOCK_ESCALATION = TABLE)
GO


-- ----------------------------
-- Table structure for playlist_song
-- ----------------------------
IF EXISTS (SELECT * FROM sys.all_objects WHERE object_id = OBJECT_ID(N'[dbo].[playlist_song]') AND type IN ('U'))
	DROP TABLE [dbo].[playlist_song]
GO

CREATE TABLE [dbo].[playlist_song] (
  [playlist_id] int  NOT NULL,
  [song_id] int  NOT NULL
)
GO

ALTER TABLE [dbo].[playlist_song] SET (LOCK_ESCALATION = TABLE)
GO


-- ----------------------------
-- Table structure for song
-- ----------------------------
IF EXISTS (SELECT * FROM sys.all_objects WHERE object_id = OBJECT_ID(N'[dbo].[song]') AND type IN ('U'))
	DROP TABLE [dbo].[song]
GO

CREATE TABLE [dbo].[song] (
  [song_id] int  IDENTITY(1,1) NOT NULL,
  [song_title] nvarchar(255) COLLATE Danish_Norwegian_CI_AS  NOT NULL,
  [song_artist] nvarchar(255) COLLATE Danish_Norwegian_CI_AS  NULL,
  [song_filepath] nvarchar(255) COLLATE Danish_Norwegian_CI_AS  NOT NULL,
  [song_length] float(53)  NULL,
  [category_id] int  NULL
)
GO

ALTER TABLE [dbo].[song] SET (LOCK_ESCALATION = TABLE)
GO


-- ----------------------------
-- Auto increment value for category
-- ----------------------------
DBCC CHECKIDENT ('[dbo].[category]', RESEED, 1)
GO


-- ----------------------------
-- Primary Key structure for table category
-- ----------------------------
ALTER TABLE [dbo].[category] ADD CONSTRAINT [PK__category__D54EE9B40A231809] PRIMARY KEY CLUSTERED ([category_id])
WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON)  
ON [PRIMARY]
GO


-- ----------------------------
-- Auto increment value for playlist
-- ----------------------------
DBCC CHECKIDENT ('[dbo].[playlist]', RESEED, 1)
GO


-- ----------------------------
-- Primary Key structure for table playlist
-- ----------------------------
ALTER TABLE [dbo].[playlist] ADD CONSTRAINT [PK__playlist__FB9C1410FCC9BAF9] PRIMARY KEY CLUSTERED ([playlist_id])
WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON)  
ON [PRIMARY]
GO


-- ----------------------------
-- Foreign Keys structure for table playlist_song
-- ----------------------------
ALTER TABLE [dbo].[playlist_song] ADD CONSTRAINT [fk_playlist_id] FOREIGN KEY ([playlist_id]) REFERENCES [dbo].[playlist] ([playlist_id]) ON DELETE CASCADE ON UPDATE CASCADE
GO

ALTER TABLE [dbo].[playlist_song] ADD CONSTRAINT [fk_song_id] FOREIGN KEY ([song_id]) REFERENCES [dbo].[song] ([song_id]) ON DELETE CASCADE ON UPDATE CASCADE
GO


-- ----------------------------
-- Auto increment value for song
-- ----------------------------
DBCC CHECKIDENT ('[dbo].[song]', RESEED, 4)
GO


-- ----------------------------
-- Primary Key structure for table song
-- ----------------------------
ALTER TABLE [dbo].[song] ADD CONSTRAINT [PK__song__A535AE1CDF129573] PRIMARY KEY CLUSTERED ([song_id])
WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON)  
ON [PRIMARY]
GO

