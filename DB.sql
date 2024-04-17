USE [hoc_viec]
GO
/****** Object:  UserDefinedFunction [dbo].[RemoveVietnameseSigns]    Script Date: 2024-04-17 2:17:17 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO

CREATE FUNCTION [dbo].[RemoveVietnameseSigns] (@string NVARCHAR(4000))
RETURNS NVARCHAR(4000)
AS
BEGIN
  DECLARE @pattern NVARCHAR(50)
  SET @pattern = '%[áàảãạâấầẩẫậăắằẳẵặÁÀẢÃẠÂẤẦẨẪẬĂẮẰẲẴẶéèẻẽẹêếềểễệÉÈẺẼẸÊẾỀỂỄỆíìỉĩịÍÌỈĨỊóòỏõọôốồổỗộơớờởỡợÓÒỎÕỌÔỐỒỔỖỘƠỚỜỞỠỢúùủũụưứừửữựÚÙỦŨỤƯỨỪỬỮỰýỳỷỹỵÝỲỶỸỴ]%'
  WHILE PATINDEX(@pattern, @string) > 0
  BEGIN
    SET @string = STUFF(@string, PATINDEX(@pattern, @string), 1, '')
  END
  RETURN @string
END
GO
/****** Object:  Table [dbo].[dia_chi]    Script Date: 2024-04-17 2:17:17 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[dia_chi](
	[id] [uniqueidentifier] NOT NULL,
	[huyen] [nvarchar](255) NULL,
	[tinh] [nvarchar](255) NULL,
	[xa] [nvarchar](255) NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[du_an]    Script Date: 2024-04-17 2:17:17 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[du_an](
	[id] [uniqueidentifier] NOT NULL,
	[tenduan] [nvarchar](max) NULL,
 CONSTRAINT [PK_du_an] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
/****** Object:  Table [dbo].[luong]    Script Date: 2024-04-17 2:17:17 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[luong](
	[id] [uniqueidentifier] NOT NULL,
	[idnhanvien] [uniqueidentifier] NULL,
	[mucluong] [bigint] NULL,
	[ngaybatdau] [date] NULL,
	[ngayketthuc] [date] NULL,
	[trangthai] [bit] NULL,
	[theloai] [nvarchar](max) NULL,
 CONSTRAINT [PK_luong] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
/****** Object:  Table [dbo].[nhan_vien]    Script Date: 2024-04-17 2:17:17 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[nhan_vien](
	[gioitinh] [bit] NULL,
	[diachi] [uniqueidentifier] NULL,
	[id] [uniqueidentifier] NOT NULL,
	[name] [nvarchar](255) NULL,
	[namsinh] [date] NULL,
	[sdt] [nvarchar](255) NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[nhan_vien_du_an]    Script Date: 2024-04-17 2:17:17 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[nhan_vien_du_an](
	[id] [uniqueidentifier] NOT NULL,
	[idnhanvien] [uniqueidentifier] NULL,
	[idduan] [uniqueidentifier] NULL,
 CONSTRAINT [PK_nhan_vien_du_an] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[tai_khoan]    Script Date: 2024-04-17 2:17:17 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[tai_khoan](
	[id] [uniqueidentifier] NOT NULL,
	[idnhanvien] [uniqueidentifier] NULL,
	[taikhoan] [nvarchar](50) NULL,
	[matkhau] [nvarchar](50) NULL,
 CONSTRAINT [PK_tai_khoan] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
INSERT [dbo].[dia_chi] ([id], [huyen], [tinh], [xa]) VALUES (N'cc7fe01a-22c8-4ff5-b878-03c7c0bfec8d', N'a', N'á', N'ạ')
INSERT [dbo].[dia_chi] ([id], [huyen], [tinh], [xa]) VALUES (N'64390d11-2fd9-49a2-8adc-0b130e178010', N'Yên Lạc ', N'Vinh Phúc', N'Vinh Tường')
INSERT [dbo].[dia_chi] ([id], [huyen], [tinh], [xa]) VALUES (N'92682558-82a7-4bb5-ae2c-0c60181e812b', N'Bảo Lạc', N'Lâm Đồng', N'Lên Đồng')
INSERT [dbo].[dia_chi] ([id], [huyen], [tinh], [xa]) VALUES (N'ef04768a-e59b-41e5-8e60-0f73255cba1f', N'a', N'á', N'ấ')
INSERT [dbo].[dia_chi] ([id], [huyen], [tinh], [xa]) VALUES (N'273de3ac-bfeb-4113-b82a-3e978509e562', N'Hạ Hòa', N'Phú Thọ', N'Hạ Hạ')
INSERT [dbo].[dia_chi] ([id], [huyen], [tinh], [xa]) VALUES (N'586c681c-e449-4d3a-a080-66b669326aae', N'Phúc Lộc', N'Yên Bái', N'Phúc Yên')
INSERT [dbo].[dia_chi] ([id], [huyen], [tinh], [xa]) VALUES (N'8cbffd75-7201-4f3a-a929-815ad5b61fe9', N'Lào Cai', N'Lào Cai', N'Cốc Lếu')
INSERT [dbo].[dia_chi] ([id], [huyen], [tinh], [xa]) VALUES (N'72d7b49e-8f96-41d5-b959-8672fd441168', N'Bình Biên', N'Hà Khẩu', N'Quảng Ninh')
INSERT [dbo].[dia_chi] ([id], [huyen], [tinh], [xa]) VALUES (N'4f2b9b68-73ca-4569-84c3-8c3212c306be', N'Hà Nội ', N'ô ', N'ô')
INSERT [dbo].[dia_chi] ([id], [huyen], [tinh], [xa]) VALUES (N'57382a00-5211-454c-95ae-a65b9370b1ad', N'Vĩnh yên', N'Vĩnh Phúc', N'Vinh')
INSERT [dbo].[dia_chi] ([id], [huyen], [tinh], [xa]) VALUES (N'6a4eff21-f489-467f-aebe-a854ce5ff24a', N'Thạch Đà', N'Vĩnh Phúc', N'Thạch Thất')
INSERT [dbo].[dia_chi] ([id], [huyen], [tinh], [xa]) VALUES (N'3b441d13-e5c7-4fc2-aa0c-a88eb820487e', N'TP Lào Cai', N'Lào cai', N'Bắc Cường')
INSERT [dbo].[dia_chi] ([id], [huyen], [tinh], [xa]) VALUES (N'50e0cab9-7d23-495c-995d-ab5ab788c853', N'Phúc Lộc', N'Yên Bái', N'Phúc Yên')
INSERT [dbo].[dia_chi] ([id], [huyen], [tinh], [xa]) VALUES (N'ec663fc8-161d-4932-a0de-ef5bf7ec302d', N'Cầu Giấy', N'Hà Nội', N'Yên Hòa')
GO
INSERT [dbo].[luong] ([id], [idnhanvien], [mucluong], [ngaybatdau], [ngayketthuc], [trangthai], [theloai]) VALUES (N'ffa024db-be9e-457b-aa01-170d8c0338ae', NULL, 5000000, CAST(N'2024-01-01' AS Date), CAST(N'2024-02-02' AS Date), 1, N'Lương phụ cấp ăn trưa')
INSERT [dbo].[luong] ([id], [idnhanvien], [mucluong], [ngaybatdau], [ngayketthuc], [trangthai], [theloai]) VALUES (N'd8af4c0a-4dae-4e57-9455-3bb83b5c38d0', NULL, 10000000, CAST(N'2022-02-01' AS Date), NULL, 1, N'Thưởng doanh số')
GO
INSERT [dbo].[nhan_vien] ([gioitinh], [diachi], [id], [name], [namsinh], [sdt]) VALUES (0, N'4f2b9b68-73ca-4569-84c3-8c3212c306be', N'f9419c11-da21-45c4-8059-1a870813499c', N'Nguyễn Xuân Lâm', CAST(N'2024-04-15' AS Date), N'093273771')
INSERT [dbo].[nhan_vien] ([gioitinh], [diachi], [id], [name], [namsinh], [sdt]) VALUES (0, N'ec663fc8-161d-4932-a0de-ef5bf7ec302d', N'6a09585f-8260-4254-95e9-1fee15300168', N'Huy bùi', CAST(N'2015-06-10' AS Date), N'0982773662')
INSERT [dbo].[nhan_vien] ([gioitinh], [diachi], [id], [name], [namsinh], [sdt]) VALUES (0, N'64390d11-2fd9-49a2-8adc-0b130e178010', N'232f439f-8372-4a4a-8038-2664f3505cb7', N'Quang Hưng Manh', CAST(N'2024-04-15' AS Date), N'0293873663')
INSERT [dbo].[nhan_vien] ([gioitinh], [diachi], [id], [name], [namsinh], [sdt]) VALUES (0, N'64390d11-2fd9-49a2-8adc-0b130e178010', N'04c77e72-a4a5-4aa2-aaec-27e5f1682d26', N'Nguyễn Minh Ngọc', CAST(N'2011-05-04' AS Date), N'0987654')
INSERT [dbo].[nhan_vien] ([gioitinh], [diachi], [id], [name], [namsinh], [sdt]) VALUES (1, N'72d7b49e-8f96-41d5-b959-8672fd441168', N'03af1173-5d19-4f5b-b094-45707b6bda89', N'Nguyễn Hồng Phúc', CAST(N'2024-04-12' AS Date), N'0982837136')
INSERT [dbo].[nhan_vien] ([gioitinh], [diachi], [id], [name], [namsinh], [sdt]) VALUES (1, N'6a4eff21-f489-467f-aebe-a854ce5ff24a', N'fa5879d8-f80b-4e47-998e-4c9d3e85113f', N'Bắc Boi', CAST(N'2024-04-08' AS Date), N'0982372637')
INSERT [dbo].[nhan_vien] ([gioitinh], [diachi], [id], [name], [namsinh], [sdt]) VALUES (0, N'64390d11-2fd9-49a2-8adc-0b130e178010', N'4ceb7fe9-a4ed-40e5-a463-544eda1674a0', N'Tùng', CAST(N'2002-01-01' AS Date), N'999999')
INSERT [dbo].[nhan_vien] ([gioitinh], [diachi], [id], [name], [namsinh], [sdt]) VALUES (1, N'64390d11-2fd9-49a2-8adc-0b130e178010', N'261f829f-5420-4d1d-b042-593b98f36da0', N'Nguyễn Trường Giang', CAST(N'2024-04-11' AS Date), N'092837631')
INSERT [dbo].[nhan_vien] ([gioitinh], [diachi], [id], [name], [namsinh], [sdt]) VALUES (0, N'64390d11-2fd9-49a2-8adc-0b130e178010', N'630696f0-4345-464e-b00d-5adad9de5714', N'Nguyễn Quang Anh', CAST(N'2024-04-02' AS Date), N'09382736')
INSERT [dbo].[nhan_vien] ([gioitinh], [diachi], [id], [name], [namsinh], [sdt]) VALUES (1, N'92682558-82a7-4bb5-ae2c-0c60181e812b', N'2079ac52-991f-428a-984d-5f154e6d75aa', N'Nguyễn Minh Ngọc', CAST(N'2024-03-21' AS Date), N'09293838')
INSERT [dbo].[nhan_vien] ([gioitinh], [diachi], [id], [name], [namsinh], [sdt]) VALUES (0, N'3b441d13-e5c7-4fc2-aa0c-a88eb820487e', N'030f8732-5b9c-486b-bf58-64ed826c6742', N'Nguyễn huy', CAST(N'2024-04-11' AS Date), N'09876434')
INSERT [dbo].[nhan_vien] ([gioitinh], [diachi], [id], [name], [namsinh], [sdt]) VALUES (0, N'273de3ac-bfeb-4113-b82a-3e978509e562', N'0c55683e-063b-41f3-9067-69e7fdedd0e2', N'Lê Ngọc Hàaaaa', CAST(N'2024-03-13' AS Date), N'098837277')
INSERT [dbo].[nhan_vien] ([gioitinh], [diachi], [id], [name], [namsinh], [sdt]) VALUES (0, N'ec663fc8-161d-4932-a0de-ef5bf7ec302d', N'be56c753-982e-49c8-9530-6c870279d80e', N'Nguyễn Đức Nam', CAST(N'2024-02-29' AS Date), N'098837273')
INSERT [dbo].[nhan_vien] ([gioitinh], [diachi], [id], [name], [namsinh], [sdt]) VALUES (1, N'64390d11-2fd9-49a2-8adc-0b130e178010', N'83d23f70-b214-4e94-be1b-8db725f01f33', N'Nguyễn Mạnh Hiếu clc', CAST(N'2024-04-16' AS Date), N'0912737123')
INSERT [dbo].[nhan_vien] ([gioitinh], [diachi], [id], [name], [namsinh], [sdt]) VALUES (1, N'586c681c-e449-4d3a-a080-66b669326aae', N'79e7f658-8880-4640-9f69-901acfa6684a', N'Lâm', CAST(N'2003-01-01' AS Date), N'88888')
INSERT [dbo].[nhan_vien] ([gioitinh], [diachi], [id], [name], [namsinh], [sdt]) VALUES (1, N'92682558-82a7-4bb5-ae2c-0c60181e812b', N'71c8a3c8-2b73-4e09-82f4-955d2c05ec1d', N'Nguyễn Minh Chuyên', CAST(N'2023-02-04' AS Date), N'092353123')
INSERT [dbo].[nhan_vien] ([gioitinh], [diachi], [id], [name], [namsinh], [sdt]) VALUES (0, NULL, N'bf8fcd24-9b34-4a51-8071-a839b21dde84', N'Tùng', CAST(N'2003-01-01' AS Date), N'32456732')
INSERT [dbo].[nhan_vien] ([gioitinh], [diachi], [id], [name], [namsinh], [sdt]) VALUES (0, N'ec663fc8-161d-4932-a0de-ef5bf7ec302d', N'3dae78bf-f068-4bb2-9b64-a96173891432', N'Trần Mạnh Hùng', CAST(N'2024-03-12' AS Date), N'0982737277')
INSERT [dbo].[nhan_vien] ([gioitinh], [diachi], [id], [name], [namsinh], [sdt]) VALUES (1, N'3b441d13-e5c7-4fc2-aa0c-a88eb820487e', N'88ff2136-ff99-4661-aae6-b5a4704cde4e', N'hiếu hhhhhh', CAST(N'2003-01-01' AS Date), N'67896789')
INSERT [dbo].[nhan_vien] ([gioitinh], [diachi], [id], [name], [namsinh], [sdt]) VALUES (0, N'8cbffd75-7201-4f3a-a929-815ad5b61fe9', N'b3b4c5c9-072d-4952-a2ea-b8ad974a7984', N'Đào Văn Hùng', CAST(N'2024-04-11' AS Date), N'097732563')
INSERT [dbo].[nhan_vien] ([gioitinh], [diachi], [id], [name], [namsinh], [sdt]) VALUES (1, N'92682558-82a7-4bb5-ae2c-0c60181e812b', N'39a6770e-880e-4e94-88f8-c5aa6034e2fc', N'Nguyễn Hồng Phúc', CAST(N'2017-09-20' AS Date), N'0293837737')
INSERT [dbo].[nhan_vien] ([gioitinh], [diachi], [id], [name], [namsinh], [sdt]) VALUES (1, N'ec663fc8-161d-4932-a0de-ef5bf7ec302d', N'92291f2b-2816-4758-a121-d065b3dca438', N'Nguyễn Thu Hà', CAST(N'2024-04-11' AS Date), N'092726262')
INSERT [dbo].[nhan_vien] ([gioitinh], [diachi], [id], [name], [namsinh], [sdt]) VALUES (1, N'4f2b9b68-73ca-4569-84c3-8c3212c306be', N'e4d81b4f-8d23-44c0-8297-decb66e930a7', N'Trần Quang Huy', CAST(N'2012-10-25' AS Date), N'097362718')
INSERT [dbo].[nhan_vien] ([gioitinh], [diachi], [id], [name], [namsinh], [sdt]) VALUES (0, N'ec663fc8-161d-4932-a0de-ef5bf7ec302d', N'014d790d-f714-4b44-b24e-dff00fbc5dfc', N'Nguyễn Ngọc Quảng', CAST(N'2024-03-05' AS Date), N'0983823717')
INSERT [dbo].[nhan_vien] ([gioitinh], [diachi], [id], [name], [namsinh], [sdt]) VALUES (1, N'ef04768a-e59b-41e5-8e60-0f73255cba1f', N'0313c1bf-3a6d-4a75-98b5-f85031092cb0', N'Lê Ngọc Huyền', CAST(N'2023-11-09' AS Date), N'0982773778')
GO
INSERT [dbo].[tai_khoan] ([id], [idnhanvien], [taikhoan], [matkhau]) VALUES (N'22e59293-9fbf-4803-9b90-7754f37989f6', N'83d23f70-b214-4e94-be1b-8db725f01f33', N'nguyenmanhhieuclc', N'123456')
INSERT [dbo].[tai_khoan] ([id], [idnhanvien], [taikhoan], [matkhau]) VALUES (N'3b7ead76-edec-4311-b6a8-81696e174b28', N'630696f0-4345-464e-b00d-5adad9de5714', N'ngu136', N'123456')
INSERT [dbo].[tai_khoan] ([id], [idnhanvien], [taikhoan], [matkhau]) VALUES (N'e5e4e81e-1641-459d-a434-c1c85dc596b2', N'232f439f-8372-4a4a-8038-2664f3505cb7', N'quanghungmanhqu265', N'123456')
INSERT [dbo].[tai_khoan] ([id], [idnhanvien], [taikhoan], [matkhau]) VALUES (N'35e916cf-a7f4-43dd-abd1-d6d8e9dec623', N'f9419c11-da21-45c4-8059-1a870813499c', NULL, NULL)
GO
ALTER TABLE [dbo].[dia_chi] ADD  CONSTRAINT [DF_dia_chi_id]  DEFAULT (newid()) FOR [id]
GO
ALTER TABLE [dbo].[du_an] ADD  CONSTRAINT [DF_du_an_id]  DEFAULT (newid()) FOR [id]
GO
ALTER TABLE [dbo].[luong] ADD  CONSTRAINT [DF_luong_id]  DEFAULT (newid()) FOR [id]
GO
ALTER TABLE [dbo].[nhan_vien] ADD  CONSTRAINT [DF_nhan_vien_id]  DEFAULT (newid()) FOR [id]
GO
ALTER TABLE [dbo].[nhan_vien_du_an] ADD  CONSTRAINT [DF_nhan_vien_du_an_id]  DEFAULT (newid()) FOR [id]
GO
ALTER TABLE [dbo].[tai_khoan] ADD  CONSTRAINT [DF_tai_khoan_id]  DEFAULT (newid()) FOR [id]
GO
ALTER TABLE [dbo].[luong]  WITH CHECK ADD  CONSTRAINT [FK_luong_nhan_vien] FOREIGN KEY([idnhanvien])
REFERENCES [dbo].[nhan_vien] ([id])
GO
ALTER TABLE [dbo].[luong] CHECK CONSTRAINT [FK_luong_nhan_vien]
GO
ALTER TABLE [dbo].[nhan_vien]  WITH CHECK ADD  CONSTRAINT [FKsuphmk9p0d51jor7t40g656kx] FOREIGN KEY([diachi])
REFERENCES [dbo].[dia_chi] ([id])
GO
ALTER TABLE [dbo].[nhan_vien] CHECK CONSTRAINT [FKsuphmk9p0d51jor7t40g656kx]
GO
ALTER TABLE [dbo].[nhan_vien_du_an]  WITH CHECK ADD  CONSTRAINT [FK_nhan_vien_du_an_du_an] FOREIGN KEY([idduan])
REFERENCES [dbo].[du_an] ([id])
GO
ALTER TABLE [dbo].[nhan_vien_du_an] CHECK CONSTRAINT [FK_nhan_vien_du_an_du_an]
GO
ALTER TABLE [dbo].[nhan_vien_du_an]  WITH CHECK ADD  CONSTRAINT [FK_nhan_vien_du_an_nhan_vien] FOREIGN KEY([idnhanvien])
REFERENCES [dbo].[nhan_vien] ([id])
GO
ALTER TABLE [dbo].[nhan_vien_du_an] CHECK CONSTRAINT [FK_nhan_vien_du_an_nhan_vien]
GO
ALTER TABLE [dbo].[tai_khoan]  WITH CHECK ADD  CONSTRAINT [FK_tai_khoan_nhan_vien] FOREIGN KEY([idnhanvien])
REFERENCES [dbo].[nhan_vien] ([id])
GO
ALTER TABLE [dbo].[tai_khoan] CHECK CONSTRAINT [FK_tai_khoan_nhan_vien]
GO
/****** Object:  StoredProcedure [dbo].[search]    Script Date: 2024-04-17 2:17:17 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO

CREATE PROC [dbo].[search]
@jsonArray NVARCHAR(MAX)
AS
BEGIN
DECLARE @table TABLE (
name NVARCHAR(50),
sdt NVARCHAR(20),
huyen NVARCHAR(50),
fromDate DATE,
toDate DATE,
pageNumber INT,
pageSize INT
)

-- Chuyển chuỗi rỗng thành NULL
IF @jsonArray = ''
BEGIN
SET @jsonArray = NULL
END

-- Ép kiểu @jsonArray thành NVARCHAR trước khi sử dụng OPENJSON
INSERT INTO @table
SELECT * FROM OPENJSON(CAST(@jsonArray AS NVARCHAR(MAX)))
WITH (
name NVARCHAR(50),
sdt NVARCHAR(20),
huyen NVARCHAR(50),
fromDate DATE,
toDate DATE,
pageNumber INT,
pageSize INT
)

DECLARE @name NVARCHAR(50)
DECLARE @sdt NVARCHAR(20)
DECLARE @huyen NVARCHAR(50)
DECLARE @fromDate DATE
DECLARE @toDate DATE
DECLARE @pageNumber INT
DECLARE @pageSize INT

DECLARE cur CURSOR FOR
SELECT name, sdt, huyen, fromDate, toDate, pageNumber, pageSize FROM @table

OPEN cur
FETCH NEXT FROM cur INTO @name, @sdt, @huyen, @fromDate, @toDate, @pageNumber, @pageSize

WHILE @@FETCH_STATUS = 0
BEGIN
-- Chuyển chuỗi rỗng thành NULL
IF @name = '' SET @name = NULL;
IF @sdt = '' SET @sdt = NULL;
IF @huyen = '' SET @huyen = NULL;
IF @fromDate = '' SET @fromDate = NULL;
IF @toDate = '' SET @toDate = NULL;

SELECT nhan_vien.id, gioitinh, namsinh, name, huyen, tinh, xa, sdt
FROM nhan_vien
JOIN dia_chi ON nhan_vien.diachi = dia_chi.id
WHERE (@name IS NULL OR nhan_vien.name LIKE '%' + @name + '%')
AND (@sdt IS NULL OR nhan_vien.sdt LIKE '%' + @sdt + '%')
AND (@huyen IS NULL OR dia_chi.huyen LIKE '%' + @huyen + '%')
AND (@fromDate IS NULL OR nhan_vien.namsinh >= @fromDate)
AND (@toDate IS NULL OR nhan_vien.namsinh <= @toDate)
ORDER BY name
OFFSET (CASE WHEN @pageNumber <= 0 THEN 0 ELSE (@pageNumber - 1) * @pageSize END) ROWS
FETCH NEXT @pageSize ROWS ONLY;

FETCH NEXT FROM cur INTO @name, @sdt, @huyen, @fromDate, @toDate, @pageNumber, @pageSize
END

CLOSE cur
DEALLOCATE cur
END
GO
/****** Object:  StoredProcedure [dbo].[SPTK]    Script Date: 2024-04-17 2:17:17 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROC [dbo].[SPTK]
@name NVARCHAR(20),
@sdt NVARCHAR(20),
@huyen NVARCHAR(20)

AS IF  @name is null OR @sdt is null or @huyen is null
print N'Phải nhập đủ'
else
select gioitinh, namsinh, name, huyen, tinh, xa 
FROM nhan_vien JOIN dia_chi ON nhan_vien.diachi = dia_chi.id 
WHERE @sdt = nhan_vien.sdt or @name = nhan_vien.name or @huyen = dia_chi.huyen
GO
