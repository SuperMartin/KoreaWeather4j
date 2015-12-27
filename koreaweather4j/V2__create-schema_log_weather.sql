USE [korea_weather4j]
GO

/****** Object:  Table [dbo].[LOG_WEATHER]    Script Date: 2015-11-17 오전 9:37:31 ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

SET ANSI_PADDING ON
GO

CREATE TABLE [dbo].[LOG_WEATHER](
	[id] [varchar](20) NULL,
	[date] [varchar](20) NULL,
	[pointName] [varchar](20) NULL,
	[latitude] [varchar](50) NULL,
	[longtitude] [varchar](50) NULL,
	[altitude] [varchar](50) NULL,
	[rain] [varchar](20) NULL,
	[rain15] [varchar](20) NULL,
	[rain60] [varchar](20) NULL,
	[rain6h] [varchar](20) NULL,
	[rain12h] [varchar](20) NULL,
	[rain24h] [varchar](20) NULL,
	[temperature] [varchar](20) NULL,
	[windDirection1] [varchar](20) NULL,
	[windDirection1c] [varchar](20) NULL,
	[windSpeed1] [varchar](20) NULL,
	[windDirection10] [varchar](20) NULL,
	[windDirection10c] [varchar](20) NULL,
	[windSpeed10] [varchar](20) NULL,
	[humidity] [varchar](20) NULL,
	[pressure] [varchar](20) NULL,
	[pointAddress] [varchar](50) NULL
) ON [PRIMARY]

GO

SET ANSI_PADDING OFF
GO

EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'아이디' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'LOG_WEATHER', @level2type=N'COLUMN',@level2name=N'id'
GO

EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'날짜' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'LOG_WEATHER', @level2type=N'COLUMN',@level2name=N'date'
GO

EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'수집 지역' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'LOG_WEATHER', @level2type=N'COLUMN',@level2name=N'pointName'
GO

EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'위도' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'LOG_WEATHER', @level2type=N'COLUMN',@level2name=N'latitude'
GO

EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'경도' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'LOG_WEATHER', @level2type=N'COLUMN',@level2name=N'longtitude'
GO

EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'고도' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'LOG_WEATHER', @level2type=N'COLUMN',@level2name=N'altitude'
GO

EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'강수량과 별도로 준비된 강수감시센서에서 관측한 강수 유무-- 비가 온날(O), 비가 안온날(.)' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'LOG_WEATHER', @level2type=N'COLUMN',@level2name=N'rain'
GO

EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'자료시간에서 과거 15분간 내린 강수의 양(mm)' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'LOG_WEATHER', @level2type=N'COLUMN',@level2name=N'rain15'
GO

EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'자료시간에서 과거 60분간 내린 강수의 양(mm)' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'LOG_WEATHER', @level2type=N'COLUMN',@level2name=N'rain60'
GO

EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'자료시간에서 과거 6시간 내린 강수의 양(mm)' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'LOG_WEATHER', @level2type=N'COLUMN',@level2name=N'rain6h'
GO

EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'자료시간에서 과거 12시간 내린 강수의 양(mm)' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'LOG_WEATHER', @level2type=N'COLUMN',@level2name=N'rain12h'
GO

EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'오늘 00시 00분부터 자료시간까지 내린 강수의 양(mm)' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'LOG_WEATHER', @level2type=N'COLUMN',@level2name=N'rain24h'
GO

EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'현재 기온(C)' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'LOG_WEATHER', @level2type=N'COLUMN',@level2name=N'temperature'
GO

EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N' 1분풍향(degree, 16방위)' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'LOG_WEATHER', @level2type=N'COLUMN',@level2name=N'windDirection1'
GO

EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N' 1분풍향 방향' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'LOG_WEATHER', @level2type=N'COLUMN',@level2name=N'windDirection1c'
GO

EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'1분평균 풍속(m/s)' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'LOG_WEATHER', @level2type=N'COLUMN',@level2name=N'windSpeed1'
GO

EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'10분평균 풍향(degree, 16방위)' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'LOG_WEATHER', @level2type=N'COLUMN',@level2name=N'windDirection10'
GO

EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'10분평균 풍향 방향' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'LOG_WEATHER', @level2type=N'COLUMN',@level2name=N'windDirection10c'
GO

EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'10분평균 풍속(m/s)' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'LOG_WEATHER', @level2type=N'COLUMN',@level2name=N'windSpeed10'
GO

EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'현재 습도(%)' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'LOG_WEATHER', @level2type=N'COLUMN',@level2name=N'humidity'
GO

EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'현재 해면기압(hPa)' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'LOG_WEATHER', @level2type=N'COLUMN',@level2name=N'pressure'
GO

EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'DNLCL' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'LOG_WEATHER', @level2type=N'COLUMN',@level2name=N'pointAddress'
GO