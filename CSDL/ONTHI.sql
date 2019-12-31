USE master					
	GO					
	IF  EXISTS (SELECT * FROM SYSdatabases  WHERE name = N'ONTHI')					
	Drop Database ONTHI					
	Go

create database ONTHI
go
use ONTHI
go

if exists (select * from sysobjects where name = N'TaiKhoan')
drop table TaiKhoan
go
create table TaiKhoan
(
	TenTaiKhoan varchar(50) not null,
	MatKhau varchar(50) not null,
	Ten Nvarchar(50) not null,
	VaiTro varchar(20) not null,
	constraint PK_TaiKhoan priMAry key (TenTaiKhoan)
)
go

insert into TaiKhoan(TenTaiKhoan,MatKhau,Ten,VaiTro) values (N'hai',N'hai',N'Tong Duy Hai',N'hocsinh'),(N'anh',N'anh',N'Nguyen Phuong Anh',N'admin'),(N'binh',N'binh',N'Dang Thanh Binh',N'giaovien')

if exists (select * from sysobjects where name = N'MonHoc')
drop table MonHoc
go
create table MonHoc
(
	MaMon varchar(15) not null,
	TenMon nvarchar(50) not null,
	constraint PK_MonHoc priMAry key (MaMon)
)
go
insert into MonHoc(MaMon,TenMon) values(N'toan',N'Toán'),(N'ly',N'Vật lý'),(N'hoa',N'Hóa học')

if exists (select * from sysobjects where name = N'CauHoi')
drop table CauHoi
go
create table CauHoi
(
	MaCH int identity not null,
	CauHoi nvarchar(max) not null,
	MaMon varchar(15) not null,
	MucDo nvarchar(15) not null,
	DapAn1 nvarchar(max) not null,
	DapAn2 nvarchar(max) not null,
	DapAn3 nvarchar(max) not null,
	DapAn4 nvarchar(max) not null,
	DapAnDung nvarchar(max) null,
	constraint PK_CauHoi priMAry key (MaCH),
	constraint FK_CauHoi_MonHoc foreign key (MaMon) references MonHoc (MaMon)
)
go

insert into CauHoi(CauHoi,MaMon,MucDo,DapAn1,DapAn2,DapAn3,DapAn4) values
(N'1+1=?',N'toan',N'Dễ',N'2',N'3',N'4',N'5'),
(N'2+2=?',N'toan',N'Dễ',N'4',N'3',N'2',N'1'),
(N'3+3=?',N'toan',N'Dễ',N'6',N'7',N'8',N'9'),
(N'4+4=?',N'toan',N'Dễ',N'8',N'9',N'10',N'5'),
(N'5+5=?',N'toan',N'Dễ',N'10',N'2',N'4',N'5'),

(N'2*2=?',N'toan',N'Trung bình',N'4',N'3',N'2',N'5'),
(N'2*3=?',N'toan',N'Trung bình',N'6',N'7',N'2',N'5'),
(N'2*4=?',N'toan',N'Trung bình',N'8',N'9',N'5',N'6'),
(N'2*5=?',N'toan',N'Trung bình',N'10',N'3',N'2',N'5'),
(N'2*6=?',N'toan',N'Trung bình',N'12',N'3',N'2',N'5'),

(N'100+100=?',N'toan',N'Khó',N'10000',N'110909',N'36522',N'1000'),
(N'100/2=?',N'toan',N'Khó',N'50',N'55',N'76',N'333'),
(N'100*2-1=?',N'toan',N'Khó',N'199',N'110909',N'36522',N'1000'),
(N'15 chia cho 5 dư bao nhiêu ?',N'toan',N'Khó',N'0',N'1',N'4',N'3'),
(N'9*9=?',N'toan',N'Khó',N'81',N'82',N'83',N'84')



INSERT [dbo].[CauHoi] ([CauHoi], [MaMon], [MucDo], [DapAn1], [DapAn2], [DapAn3], [DapAn4]) VALUES 
( N' Phát biểu nào sau đây về oxi là không đúng ?', N'hoa', N'Dễ', N'Oxi là phi kim hoạt động hóa học rất mạnh, nhất là ở nhiệt độ cao', N'Oxi tan nhiều trong nước ', N'Oxi không có mùi và không có màu
', N'Oxi cần thiết cho sự sống
'),
 ( N' Phần trăm về khối lượng của oxi cao nhất trong oxit nào cho dưới đây ?', N'hoa', N'Trung Bình', N'CuO ', N' ZnO', N'PbO ', N'MgO '),
( N'Oxit của phi kim nào dưới đây không phải là oxit axit ?', N'hoa', N'Trung Bình', N'SO2 ', N'P2O5', N'CO ', N' N2O5 '),
( N'Trong phòng thí nghiệm người ta điều chế O2 bằng cách nhiệt phân KClO3 hoặc KMnO4 vì chúng có những đặc điểm quan trọng nhất là : ', N'hoa', N'Trung Bình', N'Dễ kiếm, rẽ tiền', N'Giàu oxi và dễ phân hủy ra oxi', N'Phù hợp với thiết bị hiện đại ', N'Không độc hại
'),
 ( N' Có 4 ống nghiệm, mỗi ống đựng một chất khí khác nhau, chúng được úp ngược trong các chậu nước, sau một thời gian quan sát ta thấy nước
dâng lên trong ống nghiệm như sau:
- Khí số 1 trong ống nghiệm vẫn còn nguyên
- Khí số 2 trong ống nghiệm chỉ còn một ít
- Khí số 3 trong ống nghiệm không còn
- Khí số 4 trong ống nghiệm còn phân nữa
 Khí có độ tan lớn nhất là:', N'hoa', N'Trung Bình', N' Khí số 1 ', N'Khí số 2 ', N' Khí số 3 ', N'Khí số 4
'),
( N'Dãy chỉ gồm các oxit axit là :', N'hoa', N'Trung Bình', N'CO, CO2 , MnO2 , Al2O3 , P2O5', N'CO2, SO2 , P2O5, SO3 , N2O5', N'FeO , Mn2O7 , SiO2 , CaO, Fe2O3', N'Na2O , BaO , H2O, ZnO, CuO

'),
 ( N'Hỗn hợp nào sau đây có thể tách riêng các chất thành phần bằng cách cho hỗn hợp và nước, sau
đó khuấy kĩ và lọc?', N'hoa', N'Khó', N'Bột đá vôi và muối ăn', N'Bột than và bột sắt', N'Đường và muối', N'Giấm và rượu

'),
( N'Tính chất nào của chất trong số các chất sau đây có thể biết được bằng cách quan sát trực tiếp
mà không phảI dùng dụng cụ đo hay làm thí nghiệm?', N'hoa', N'Khó', N'Màu sắc', N'Tính tan trong nước', N'Khối lượng riêng', N'Nhiệt độ nóng chảy

'),
 ( N'Dựa vào tính chất nào dưới đây mà ta khẳng định được trong ', N'hoa', N'Khó', N'Không màu, không mùi', N'Không tan trong nước', N'Khối lượng riêng', N'Nhiệt độ nóng chảy'),
( N'Rượu etylic( cồn) sôi ở 78,3
0 nước sôi ở 100
0C. Muốn tách rượu ra khỏi hỗn hợp nước có thể
dùng cách nào trong số các cách cho dưới đây?', N'hoa', N'Khó', N'Lọc', N'Bay hơi', N'Chưng cất ở nhiệt độ khoảng 80
0', N'Không tách được'),
( N'Hãy chọn công thức hoá học đúng trong số các công thức hóa học sau đây:', N'hoa', N'Khó', N'CaPO4', N' Ca2(PO4)2', N'Ca3(PO4)2', N'Ca3(PO4)3'),
( N'Nguyên tố X có hoá trị III, công thức của muối sunfat là', N'hoa', N'Dễ', N'XSO4', N'X(SO4)3', N'X2(SO4)3
', N'X3SO4
'),
( N' Biết N có hoá trị IV, hãy chọn công thức hoá học phù hợp với qui tác hoá trị trong đó có các
công thức sau:
', N'hoa', N'Dễ', N'NO', N'N2O', N'N2O3', N'NO2'),
 ( N'Chất nào sau đây là chất tinh khiết?

', N'hoa', N'Dễ', N'NaCl', N'Dung dịch NaCl
', N'Nước chanh', N' Sữa tươi'),
 ( N'Nguyên tử N có hoá trị III trong phân tử chất nào sau đây?

', N'hoa', N'Dễ', N'N2O5', N'NO2
', N'NO', N'N2O3'),
 ( N'Khi	nói	về	dao	động	cơ,	phát	biểu	nào	sau	đây	sai?', N'ly', N'Dễ', N'	Dao	động	cưỡng	bức	có	biên	độ	không	phụ	thuộc	vào	biên	độ	của	lực	cưỡng	bức', N'Dao	động	của	con	lắc	đồng	hồ	là	dao	động	duy	trì.', N'Dao	động	cưỡng	bức	có	biên	độ	không	đổi	và	có	tần	số	bằng	tần	số	của	lực	cưỡng	bức.', N'	Dao	động	tắt	dần	có	biên	độ	giảm	dần	theo	thời	gian.'),
 ( N'	Dao	động	tắt	 dần	có	biên	độ	giảm	dần	theo	thời	gian.', N'ly', N'Dễ', N'	Dao	động	tắt	dần	có	biên	độ	giảm	dần	theo	thời	gian.', N'	Dao	động	tắt	dần	có	biên	độ	giảm	dần	theo	thời	gian.', N'	Dao	động	tắt	dần	có	biên	độ	giảm	dần	theo	thời	gian.', N'	Dao	động	tắt	dần	có	biên	độ	giảm	dần	theo	thời	gian.'),
( N'Phát biểu nào sau đây là đúng khi nói về dao động tắt dần?', N'ly', N'Dễ', N'Dao động tắt dần có biên độ giảm dần theo thời gian.', N'Cơ năng của vật dao động tắt dần không đổi theo thời gian', N'Lực cản môi trường tác dụng lên vật luôn sinh công dương.', N'Dao động tắt dần là dao động chỉ chịu tác dụng của nội lực.'),
( N'Vật dao động tắt dần có', N'ly', N'Dễ', N'A.	Sau thời gian T , vật đi được quãng đường bằng 0,5 A.
A.	Sau thời gian T , vật đi được quãng đường bằng 0,5 A.
8
A.	Sau thời gian T , vật đi được quãng đường bằng 0,5 A.
cơ năng luôn giảm dần theo thời gian

', N'thế năng luôn giảm theo thời gian.', N'li độ luôn giảm dần theo thời gian.	', N' pha dao động luôn giảm dần theo thời gian'),
( N'Khi nói về dao động điều hòa, phát biểu nào sau đây đúng?', N'ly', N'Dễ', N'Hợp lực tác dụng lên vật dao động điều hòa luôn hướng về vị trí cân bằng.', N'Dao động của con lắc lò xo luôn là dao động điều hòa', N'Cơ năng của vật dao động điều hòa không phụ thuộc vào biên độ dao động', N'Dao động của con lắc đơn luôn là dao động điều hòa'),
 ( N'Khi xảy ra cộng hưởng cơ thì vật tiếp tục dao động', N'ly', N'Dễ', N'với tần số bằng tần số dao động riêng', N'mà không chịu ngoại lực tác dụng.', N'với tần số lớn hơn tần số dao động riêng', N'với tần số nhỏ hơn tần số dao động riêng.'),
( N'Dùng một thước có chia độ đến milimét đo 5 lần khoảng cách d giaữ hai điểm A v  à B đều cho cùng một giá trị là 1,345 mm. Lấy sai số dụng cụ là một độ chia nhỏ nhất. Kết quả đo được viết là', N'ly', N'Trung Bình', N'd =(1,345 0, 001) mm', N'd =(1,345  0, 0005) mm', N'd =(1345 2) mm', N'd =(1345  3) mm'),
( N'Nói về một chất điểm dao động điều hòa, phát biểu nào dưới đây đúng?', N'ly', N'Trung Bình', N'Ở VTCB, chất điểm có độ lớn vận tốc cực đại và gia tốc bằng không', N'Ở vị trí cân bằng, chất điểm có vận tốc bằng không và gia tốc cực đại.', N'Ở vị trí biên, chất điểm có vận tốc bằng không và gia tốc bằng không.', N'Ở vị trí biên, chất điểm có độ lớn vận tốc cực đại và gia tốc cực đại.'),
( N'Khi nói về dao động điều hòa của một vật, phát biểu nào sau đây đúng?', N'ly', N'Trung Bình', N'Véctơ gia tốc của vật luôn hướng về vị trí cân bằng.', N'Khi vật ở vị trí biên, gia tốc của vật bằng không.', N'Véctơ vận tốc của vật luôn hướng về vị trí cân bằng.', N'Khi đi qua vị trí cân bằng, vận tốc của vật bằng không.'),
( N'Một con lắc lò xo dao động điều hòa với tần số góc ω. Cơ năng của con lắc là một đại lượng:', N'hoa', N'Trung Bình', N'không thay đổi theo thời gian.', N'biến thiên tuần hoàn theo thời gian với tần số góc ω', N'biến thiên tuần hoàn theo thời gian với tần số góc 2ω', N'biến thiên tuần hoàn theo thời gian với tần số góc ω/2'),
( N'Trên một sợi dây có chiều dài l, hai đầu cố định, đang có sóng dừng. Trên dây có một bụng sóng. Biết vận tốc truyền sóng trên dây là v không đổi. Tần số của sóng là', N'ly', N'Trung Bình', N'v/2 l.', N'v/l.', N'2v/ l.', N'v/4 l'),
( N'Một sóng cơ lan truyền trong một môi trường. Hai điểm trên cùng một phương truyền sóng, cách nhau một khoảng bằng bước sóng có dao động.', N'ly', N'Khó', N'Cùng pha', N'Ngược pha', N'lệch pha pi/2', N'lệch pha pi/4'),
( N'Một vật dao động điều hòa có biên độ A và chu kì T, với mốc thời gian (t = 0) là lúc vật ở vị trí biên, phát biểu nào sau đây là sai?', N'ly', N'Khó', N'Sau thời gian T/8 , vật đi được quãng đường bằng 0,5 A.', N'Sau thời gian T/2 , vật đi được quãng đường bằng 2 A.', N'Sau thời gian T/4 , vật đi được quãng đường bằng A.', N'Sau thời gian T, vật đi được quãng đường bằng 4A.'),
 ( N'Một vật dao động cưỡng bức dưới tác dụng của ngoại lực F = F0cosft (với F0 và f không đổi, t tính bằng s). Tần số dao động cưỡng bức của vật là', N'ly', N'Khó', N'0,5f.', N'2pif.', N'pif', N' f'),
 ( N'Biểu thức li độ của vật dao động điều hòa có dạng x = Acos (ωt + φ), vận tốc của vật có giá trị cực đại là', N'ly', N'Khó', N'vmax = Aω', N'vmax = Aω2', N'vmax = 2Aω', N'vmax = A2ω'),
(N'Hai dao động điều hòa cùng phương, có phương trình x1 = Acos(ωt + π/3) và x2 = Acos(ωt - 2π/3) là hai dao động', N'ly', N'Khó', N'ngược pha', N'Lệch pha pi/2', N'cùng pha', N'lệch pha pi/3'),
( N'Vật dao động điều hòa theo trục Ox. Phát biểu nào sau đây đúng?', N'ly', N'Khó', N'Quỹ đạo chuyển động của vật là một đoạn thẳng.', N'Lực kéo về tác dụng vào vật không đổi.', N'Quỹ đạo chuyển động của vật là một đường hình cos.', N'Li độ của vật tỉ lệ với thời gian dao động.'),
 ( N'Dao động của con lắc đồng hồ là:', N'ly', N'Khó', N'dao động duy trì.', N'dao động cưỡng bức', N'dao động tắt dần.', N'dao động điện từ.')

if exists (select * from sysobjects where name = N'DeThi')
drop table DeThi
go
create table DeThi
(
	MaDT varchar(15) not null,
	MaMon varchar(15) not null,
	DSMaCH varchar(max) not null,
	ThoiGian int not null,
	DSDapAn nvarchar(max) not null,	
	constraint PK_DeThi priMAry key (MaDT),
	constraint FK_DeThi_MonHoc foreign key (MaMon) references MonHoc (MaMon)
)
go

if exists (select * from sysobjects where name = N'KetQua')
drop table KetQua
go
create table KetQua
(
	MaKQ int identity not null,
	TenTaiKhoan varchar(50) not null,
	MaDT varchar(15) not null,
	Diem float not null,
	constraint PK_KetQua priMAry key (MaKQ),
	constraint FK_KetQua_TaiKhoan foreign key (TenTaiKhoan) references TaiKhoan (TenTaiKhoan),
	constraint FK_KetQua_DeThi foreign key (MaDT) references DeThi (MaDT),
)
go

INSERT [dbo].[DeThi] ([MaDT], [MaMon], [DSMaCH], [ThoiGian], [DSDapAn]) VALUES (N'DT0002', N'toan', N'1;2;3;4;5;6;7;8;9;10;11;12;13;14;15', 1200, N'2143;3421;3412;3124;3124;1423;4312;3214;3124;2134;3241;2134;2143;4321;4123')

	CREATE PROC [dbo].[sp_BangDiem]
	@MaMon varchar(15),
	@MaDT varchar(15)
AS BEGIN  
	SELECT 
		dt.MaMon,
		dt.MaDT,      
		kq.Diem 
	FROM Ketqua kq JOIN DeThi dt ON dt.MaDT = kq.MaDT 
	WHERE dt.MaMon= @MaMon and dt.MaDT = @MaDT
	ORDER BY kq.Diem DESC
END 
GO

select avg (KetQua.Diem) as TB from KetQua join DeThi on DeThi.MaDT = KetQua.MaDT where MaMon= N'toan'
