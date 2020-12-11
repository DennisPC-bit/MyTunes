/*
 Navicat Premium Data Transfer

 Source Server         : skole
 Source Server Type    : SQL Server
 Source Server Version : 11007493
 Source Host           : 10.176.111.31:1433
 Source Catalog        : mytunesDennisCarloChristian
 Source Schema         : dbo

 Target Server Type    : SQL Server
 Target Server Version : 11007493
 File Encoding         : 65001

 Date: 11/12/2020 10:50:41
*/


-- ----------------------------
-- Table structure for category
-- ----------------------------
IF EXISTS (SELECT * FROM sys.all_objects WHERE object_id = OBJECT_ID(N'[dbo].[category]') AND type IN ('U'))
	DROP TABLE [dbo].[category]
GO

CREATE TABLE [dbo].[category] (
  [category_id] int  NOT NULL,
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
  [playlist_id] int  NOT NULL,
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
  [song_id] int  NOT NULL,
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
-- Primary Key structure for table category
-- ----------------------------
ALTER TABLE [dbo].[category] ADD CONSTRAINT [PK__category__D54EE9B4FFC4B68B] PRIMARY KEY CLUSTERED ([category_id])
WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON)  
ON [PRIMARY]
GO


-- ----------------------------
-- Primary Key structure for table playlist
-- ----------------------------
ALTER TABLE [dbo].[playlist] ADD CONSTRAINT [PK__playlist__FB9C14100DF76110] PRIMARY KEY CLUSTERED ([playlist_id])
WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON)  
ON [PRIMARY]
GO


-- ----------------------------
-- Indexes structure for table playlist_song
-- ----------------------------
CREATE NONCLUSTERED INDEX [playlist_id]
ON [dbo].[playlist_song] (
  [playlist_id] ASC
)
GO

CREATE NONCLUSTERED INDEX [fk_song_id]
ON [dbo].[playlist_song] (
  [song_id] ASC
)
GO


-- ----------------------------
-- Foreign Keys structure for table playlist_song
-- ----------------------------
ALTER TABLE [dbo].[playlist_song] ADD CONSTRAINT [fk_playlist_id] FOREIGN KEY ([playlist_id]) REFERENCES [dbo].[playlist] ([playlist_id]) ON DELETE NO ACTION ON UPDATE NO ACTION
GO

