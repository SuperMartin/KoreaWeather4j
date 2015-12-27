USE [korea_weather4j]
GO

/****** Object:  Table [dbo].[LOG_WEATHER2]    Script Date: 2015-11-17 오전 11:11:16 ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

SET ANSI_PADDING ON
GO

CREATE TABLE [dbo].[LOG_WEATHER2](
	[date] [varchar](100) NOT NULL,
	[point_name] [varchar](20) NULL,
	[status] [varchar](20) NULL,
	[sight] [varchar](20) NULL,
	[cloud] [varchar](20) NULL,
	[cloud_mid] [varchar](20) NULL,
	[temperature] [varchar](20) NULL,
 CONSTRAINT [PK_LOG_WEATHER2] PRIMARY KEY CLUSTERED 
(
	[date] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]

) ON [PRIMARY]

GO

SET ANSI_PADDING OFF
GO