package com.corp.myxof.relation;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class RelationshipExtractorTest {
	private RelationshipExtractor extractor;
	private Relationship relationship;
	private String content1;
	private String name1;
	private String content2;
	private String name2;	
	private String content3;
	private String name3;
	private String content4;
	private String name4;
	private String content5;
	private String name5;
	
	private String content6;
	private String name6;
	
	@Before
	public void setUp() throws Exception {
		extractor = new RelationshipExtractor();
		extractor.init();
		content1 = "荀慧生，男，京剧旦角。原名词，又名秉彝、秉超，字慧生，号留香，艺名白牡丹。出生于河北省东光县（现为阜成县）一个捻售线香的手工业之家。幼年家贫无以生计。"
				+ "1907年随父母到天津谋生，父亲将他与兄慧荣卖予小桃红梆子戏班学戏。不久其兄不堪忍受打骂私自逃走，只剩慧生，后被卖给花旦为私房徒弟，自此，荀慧生沦为家奴，吃尽苦头，但他仍以巨大的耐力与毅力坚持每天练功。"
				+ "夏天穿棉袄，冬天穿单衣，头顶大碗，足履冰水，点香火头练转眼珠，日复一日，年复一年，苦功练出了硬本领，唱、念、做、打无一不精。"
				+ "1909年，荀慧生以“白牡丹”艺名随师常在冀中、冀东一带农村市镇唱庙会和野台子戏。1910年荀慧生随师进京，先后搭庆寿和、义顺和、鸿顺和、天庆和等梆子班。"
				+ "辛亥革命前期他去天津曾同革命戏剧家王钟声，同台演出《革命家庭》、《黑奴恨》等戏，后向陈桐云、李寿山、程继先学习京昆艺术，直到十七岁才独立成班。"
				+ "1918年与刘鸿升、侯喜瑞、梅兰芳、程继先开始合作，演出《胭脂虎》、《霓虹关》等戏，又同杨小楼、余叔岩、王凤卿、高庆奎、朱桂芳等合作，并拜王瑶卿门下学习正工青衣。"
				+ "同年杨小楼应上海天蟾舞台之邀和尚小云、谭小培合作演出，请荀慧生担任“刀马旦”，公演引起轰动，人称“三小一白”（即小楼、小云、小培和牡丹）。"
				+ "荀慧生的表演生动活泼，扮相俊俏，使上海观众耳目一新，被赞为“誉满春申”，后又与周信芳、冯子和、盖叫天、小达子等人合作，演出《赵五娘》、《劈山救母》、《九曲桥》、《杨乃武与小白菜》等戏，"
				+ "名震沪上。上海国画大师吴昌硕同知名人士严独鹤、舒舍予也为宣传和扩大荀慧生的艺术影响到处奔走。荀慧生喜欢作画，1924年正式拜吴昌硕为师，"
				+ "他又向齐白石、陈半丁、傅抱石、李苦禅、王雪涛等名师求教，丰富了他的艺术生活，提高了他的艺术素养。1927年北京报界举办京剧旦行评选，"
				+ "他与梅、尚、程一起被誉为“四大名旦”，这也标志着他艺术走向成熟，得到社会肯定，赢得了观众的赞赏。他在继承传统的基础上，在剧本、唱腔、表演、念白、到服饰等方面都进行了创新的实践与探索。"
				+ "他演出的剧目有三百多出，主要包括《红娘》、《红楼二尤》、《杜十娘》、《荀灌娘》、《钗头凤》、《十三妹》、《玉堂春》、《金玉奴》、《得意缘》、《卓文君》等。"
				+ "在唱腔艺术方面荀慧生大胆破除传统局限，发挥个人嗓音特长，吸取昆、梆、汉、川等曲调旋律，大胆创新。这不是简单的一曲多用，而是从生活出发，"
				+ "从人物感情与心境出发，字正腔圆，腔随情出，令人着迷。他善于使用上滑下滑的装饰音，听来俏丽、轻盈、谐趣具有特殊的韵味。他还十分注重道白艺术，吐字清晰，声情并貌，"
				+ "他创造出融韵白、京白为一体的念白，韵调别致，具有特殊的表现力。表演方面他强调“演人不演行”，不受行当限制，根据需要进行必要的突破，"
				+ "他塑造的许多少女、少妇的艺术形象，具有大众化、生活化的特点。娇雅妩媚、清秀俊美、风格各异。建国后，荀慧生遵照周总理签署的戏曲改革指示，"
				+ "为京剧艺术改革做出了大量工作。1952年获第一届戏曲观摩大会老艺术家表演奖。他历任中国戏曲家协会艺委会副主任，北京市戏曲研究所所长，河北省梆子剧院院长，"
				+ "河北省政协委员，北京市文联常务理事，北京市戏曲编导委员会主任等职。荀慧生一生收徒之众，不计其数，"
				+ "他指导和亲自传授的后人、学生、徒弟有：吴纪敏、金淑华、李薇华、荀令莱、宋德珠、毛世来、童芷苓、李玉茹、李玉芝、吴素秋、赵燕侠、小王玉蓉、张正芳、尚明珠、厉慧敏、陆正梅、宋长荣、李妙春等，"
				+ "还有许多人虽未拜师，但多得其亲授。";
		name1 = "荀慧生";
		
		content2 = "尚小云，男，京剧旦角。名德泉，字绮霞；行二。祖籍河北南宫县，生于北京市安定门内，法通寺草场大院。平南亲王尚可喜十二世孙，隶汉军镶蓝旗。"
				+ "父元照生三子：长子德海，次子德泉（小云），三子德祿（即小小云）。幼年曾随李春福学老生，是为“把手徒弟”。1907年进入“三乐社”科班（后改名“正乐社”），"
				+ "艺名“三锡”，先习武生，又学花脸。其后因师辈们见其扮相秀丽、英俊，逐让他改学旦行，师从青衣名家孙怡云，便改艺名为“小云”。1912年在广和楼公演，尚小云的戏很受欢迎。"
				+ "1913年李际良掌三乐，更名正乐社，演于民乐。1914年冬，与孙菊仙配《三娘教子》、《战蒲关》，被评为“第一童伶”。世人称尚小云、白牡丹、芙蓉草为“正乐三杰”。"
				+ "教师初为唐茂。1916年十八岁学满出科，次年拜名青衣张芷荃为师，后又拜名昆丑陆金桂为师，习昆曲。并先后受教于戴韵芳、陈德霖、路三宝、王瑶卿等名师，艺事突飞猛进。"
				+ "尚小云早年习武生，对杨小楼的艺术十分仰慕，后来他把“杨派”武生的精湛表演，吸收融化，用在自己的旦角戏里，使其表演刚劲、挺拔，于旦角的妩媚多姿中又见阳刚之美。"
				+ "特别适于表演巾帼英雄人物。至此尚小云成为同辈旦角中，以演女中豪侠为特色的佼佼者。1918年尚小云自组“重庆社”，不断排演新剧目，"
				+ "以巾帼英雄人物为居多。如《卓文君》、《林四娘》、《秦良玉》、《墨黛》、《双阳公主》、《摩登伽女》、《相思寨》、《青城十九侠》、《虎乳飞仙传》、《红绡》、《峨嵋剑》等等，"
				+ "都是他首创演出，又成为他的独有剧目。“尚派”代表作以巾帼英雄、侠女为多，需要武功技巧，即使不具武勇的角色，尚先生也根据剧情需要采取“文戏武唱”的办法，以求火爆，"
				+ "并增进视觉上的美感。比如《昭君出塞》中的王昭君，在出塞后换乘烈马之时，就使用了串“鹞子翻身”、“趟马”疾驰，俯冲“圆场”等属于武功技巧的动作。"
				+ "比如《失子惊疯》中的胡氏娘子，在疯癫之时，便使用了如单腿抬起的“金鸡独立”三起三落的表演等。这都极为符合人物的特色情景，提高了可视性。"
				+ "因此尚先生培育后辈时就力求既专又博。1927年，由北京《顺天时报》发起选举名伶，尚小云以新编剧《摩登伽女》被选为“四大名旦”。1937年创办“荣春社”。"
				+ "不惜家产办学，培养了“荣”、“春”、“长”、“喜”两科学生，以及三个儿子。尚小云治艺严谨，无论对己对人要求十分严格，但在生活中对人十分热情，慷慨仗义，倾囊相赠。"
				+ "“荣春社”共培养学生200余名，遍布全国，在京剧演出和京剧教育岗位上发挥重大作用。1950年建立“尚小云京剧团”，任团长兼主演。"
				+ "1955年当选为中国人民政治协商会议北京市第一届委员会常委委员，并连任二、三、四届常委委员。1956年任北京市文联常委理事，北京市京剧工作者联合会副主任。"
				+ "1959年移居西安，任陕西省艺术学校艺术总指导，陕西省剧协常委理事。并出任庆祝建国十周年赴京献礼演出团团长。1960年出任中苏友好协会陕西分会理事。"
				+ "全国文教群英会陕西省赴京代表团副团长。1962年，拍摄艺术片《尚小云舞台艺术》，录制《失子惊疯》和《昭君出塞》两部影片。"
				+ "1964年受聘于陕西省京剧院，担任该院首任院长（当时陕京是全国五大京剧团体之一）。1966年“文革”开始，受到迫害冲击。"
				+ "1976年4月19日在西安逝世（逝世于西安市第一人民医院），享年76岁。较著名的亲传弟子有张蝶芬、赵晓岚、雪艳琴、张君秋（后自创“张派”）、孙荣蕙（天津）、杨荣环、"
				+ "尚长麟（次子）、李喜鸿（北京）、孙明珠（陕西，学生，未拜师）、董玉苓（北京）、李翔（北京）、鲍绮瑜（内蒙古）、周百穗（贵州）、童葆苓（北京）、尚慧敏（长孙女、佳木斯）、"
				+ "段丽君（成都）、黄玉华（女）等，再传弟子有马博敏（上海）、李莉（天津）、鞠小苏（山东）、周利（重庆）、王玲玉等。初娶花脸李寿山之女，继娶花旦王蕙芳之妹。长子尚长春攻武生，"
				+ "幼子尚长荣攻花脸，女婿任志秋攻旦角。女尚秀琴未从艺。";
		name2 = "尚小云";
		
		content3 = "杨小楼，男，京剧武生。小名三元，谱名嘉训；安徽省潜山县人。祖父杨二喜为京剧武旦，父杨月楼。幼入小荣椿科班学艺，从杨隆寿、姚增禄、杨万青学武生。"
				+ "十六岁出科，在京、津两地搭班，充当配角。后发愤用功。得义父谭鑫培和王楞仙、王福寿、张淇林、牛松山等的指点，并拜俞菊笙为师，技艺渐进。"
				+ "杨小楼之父杨月楼与俞菊笙为至交。约在1877年至1878年间，二位夫人先后有孕。杨、俞二人约定：若一男一女，则指腹为婚；若二男，则换子。"
				+ "1877年冬月，俞菊笙之子诞下；1879年八月，杨月楼之子诞下，二人换子。故杨小楼为俞菊笙之嫡子，俞振庭为杨月楼之嫡子。"
				+ "杨小楼拜师学艺，因其为俞氏亲子，故俞氏倾囊传艺。外界谓“杨小楼得其八九，俞振庭得其二三。”（翁思再所著《余叔岩传》）二十四岁搭入宝胜和班，以“小杨猴子”之名露演，"
				+ "名声渐起。又与谭鑫培同在同庆班，经谭氏奖液，成为挑大梁的武生演员。二十九岁时入升平署为外学民籍学生,备受慈禧赏识。"
				+ "他与谭鑫培、陈德霖、王瑶卿、黄润甫、梅兰芳、尚小云、荀慧生、高庆奎、余叔岩、郝寿臣等人合作，先后组建陶咏、桐馨、中兴、崇林、双胜、永胜等班。"
				+ "声誉鹊起。在当时和梅兰芳、余叔岩并称为“三贤”，成为京剧界的代表人物，享有“武生宗师”的盛誉。杨小楼在艺术上继承家学，师法俞（菊笙）、杨（隆寿），"
				+ "同时博采众长，打下武生表演技艺的全面基础，逐渐形成独树一帜的“杨派”，杨小楼的嗓音清脆洪亮，唱念均遵“奎派”风范，咬字清楚真切，间有京音，行腔朴直无华。"
				+ "唱念注意准确表达角色的感情。从现存的《霸王别姬》、《夜奔》、《野猪林·结拜》等戏的唱片中，可以领略到他唱念的神韵。杨小楼武打步法准确灵敏，无空招废式，"
				+ "更能恰当贴切地表现人物的性格，着力体现意境，追求神似，也即“武戏文唱”的杨派特点。因此，他的长靠戏《长坂坡》、《挑华车》、《铁笼山》，箭衣戏《状元印》、《八大锤》、"
				+ "《艳阳楼》，短打戏《连环套》、《骆马湖》、《安天会》，昆曲戏《夜奔》、《宁武关》、《麒麟阁》，老生戏《法门寺》、《四郎探母》、《战太平》，无一不精。"
				+ "晚期他还排演了《野猪林》、《康郎山》等戏。其一生真正收有三位弟子，在1934年正式收傅德威为入室弟子，傅得杨真传，杨在收徒当日还特地示范演出了两出戏。"
				+ "杨派武生传人有孙毓堃、刘宗杨、高盛麟、沈华轩、周瑞安等人，李万春、李少春、王金璐、厉慧良等皆宗法杨派。";
		name3 = "杨小楼";
		
		content4 = "梅兰芳，男，京剧旦角。原名澜，字畹华、浣华，号鹤鸣，乳名群儿，斋名缀玉轩，别署缀玉轩主人。祖籍江苏泰州市之东薛家庄。生于北京李铁拐斜街45号（今铁树斜街101号）。"
				+ "父明瑞，字竹芬，小生改花旦，母为杨隆寿之长女长玉。梅兰芳童年时并未表现出过人的艺术天分，相貌也很平常，两只眼睛有些近视，眼皮总下垂。"
				+ "眼睛既不能外露，又不能正视，显着无神的样子，见了生人还不会说话。因此他姑母用八个字形容他：“言不出众，貌不惊人。”七岁的梅兰芳在住家附近一个私塾就读，"
				+ "初时因为读书不太用心，成绩自然不好。开始学戏是他八岁时，请来教戏的是名小生朱素云的哥哥朱小霞，朱先生按照教青衣传统的方法，先教他唱《二进宫》。"
				+ "谁想四句极普通的老腔，教了很长时间，他总是不能上口，先生见他进步太慢，认为这孩子没有希望，就对他说：“祖师爷没给你这碗饭吃。”说罢竟拂袖而去，再也不来教他了。"
				+ "梅兰芳成名后，有一次爷俩又见面了。朱先生很不好意思地对他说“我那时真是有眼不识泰山。”梅兰芳笑着说：“您快别说了，我受您的益处太大了，要不挨您这一顿骂，"
				+ "我还不懂得发奋苦学呢！”梅兰芳幼从吴菱仙习青衣，10岁登台在北京广和楼演出《天仙配》，工花旦，1908年搭喜连成班。当年秋，喜连成班主叶春善带领他的科班在吉林演出。"
				+ "一天早晨，叶春善偕筹资组建喜连成的开明绅士牛子厚到吉林北山散步。忽然发现时用艺名“喜群”的梅兰芳在小树林里练剑。牛子厚见他仪表堂堂，气度潇洒，举止端庄，便询问其艺名。"
				+ "得知为“喜群”后，沉吟良久说：“这孩子相貌举止不俗，久后必成大器，给他更名‘梅兰芳’如何？”叶春善师徒二人欣然同意。从此，就用了“梅兰芳”这一享誉国内外的艺名。"
				+ "1911年北京各界举行京剧演员评选活动，张贴菊榜，梅兰芳名列第三名探花。1913年他首次到上海演出，在四马路大新路口丹桂第一台演出了《彩楼配》、《玉堂春》、《穆柯寨》等戏，"
				+ "初来上海就风靡了整个江南，当时里巷间有句俗话：“讨老婆要像梅兰芳，生儿子要像周信芳”。他吸收了上海文明戏、新式舞台、灯光、化妆、服装设计等改良成分，返京后创演时装新戏《孽海波澜》，"
				+ "第二年再次来沪，演了《五花洞》、《真假潘金莲》、《贵妃醉酒》等拿手好戏，一连唱了34天。回京后，梅兰芳继续排演新戏《嫦娥奔月》、《春香闹学》、《黛玉葬花》等。"
				+ "1916年第三次来沪，连唱45天，1918年后，移居上海，这是他戏剧艺术炉火纯青的顶峰时代，多次在天蟾舞台演出。综合了青衣、花旦、刀马旦的表演方式，创造了醇厚流丽的唱腔，"
				+ "形成独具一格的梅派。1915年，梅兰芳大量排演新剧目，在京剧唱腔、念白、舞蹈、音乐、服装上均进行了独树一帜的艺术创新，被称为梅派大师。"
				+ "1914年20岁时与王蕙芳师事陈德霖，是“老夫子”最喜欢的一个徒弟，因此学的最多，如《昭君出塞》、《金山寺》、《战蒲关》、《宝莲灯》、《回龙阁》、《打花鼓》、"
				+ "《风筝误》、《游园惊梦》、《刺虎》、《奇双会》等戏，陈德霖还曾陪梅兰芳演出过《金山寺》、《风筝误》等戏。1919年4月，梅兰芳应日本东京帝国剧场之邀赴日本演出，"
				+ "演出了《天女散花》、《玉簪记》等戏。一个月后回国。1921年编演新戏《霸王别姬》。1922年主持承华社。1927年北京《顺天时报》举办中国首届旦角名伶评选，"
				+ "梅兰芳因功底深厚、嗓音圆润、扮相秀美，与程砚秋、尚小云、荀慧生被举为京剧四大名旦。1930年春，梅兰芳率团赴美，在纽约、芝家哥、旧金山、洛杉矶等市献演京剧，获得巨大的成功，报纸评论称，"
				+ "中国戏不是写实的真，而是艺术的真，是一种有规矩的表演法，比生活的真更深切。在此期间，他被美国波莫纳大学和南加利福尼亚大学授予文学博士学位。"
				+ "1931年“九·一八”事变后，梅兰芳迁居上海，先暂住沧洲饭店，后迁马斯南路121号。他排演《抗金兵》、《生死恨》等剧，宣扬爱国主义。1935年他曾率团赴苏联及欧洲演出并考察国外戏剧。"
				+ "在京剧艺术家中，出访最多和在国内接待外国艺术家最多的当属梅兰芳，他把中国京剧表演艺术和艺术家谦逊、朴实的优良品质介绍给了各国人民，因此人们称他为二十世纪二十年代至五十年代中国京剧艺术的文化使节。"
				+ "抗战爆发后，日伪想借梅兰芳收买人心、点缀太平，几次要他出场均遭拒绝。梅兰芳考虑到在上海不能久留，遂于1938年赴香港。他在香港演出《梁红玉》等剧，激励人们的抗战斗志。"
				+ "1941年香港沦陷后，他安排两个孩子到大后方读书，自己于1942年返沪。为了拒绝为日伪演剧，他蓄须明志，深居简出，表现了崇高的民族气节。抗战胜利后，梅兰芳在上海复出，常演昆曲，"
				+ "1948年拍摄了彩色片《生死恨》，是中国拍摄成的第一部彩色戏曲片。上海解放后，于1949年6月应邀至北平参加第一次全国文学艺术工作者代表大会，当选为政协全国委员会常委。1950年回北京定居，"
				+ "任文化部京剧研究院院长，1951年任中国戏曲研究院院长，1952年任中国京剧院院长，并先后当选为全国人大代表。1955年，他拍摄了《梅兰芳的舞台艺术》，"
				+ "收入他各个时期的代表作《宇宙锋》、《断桥》等及他生活片断和在工厂、舞台演出的《春香闹学》等戏的片断。1956年他率中国京剧代表团到日本演出。1959年5月他在北京演出《穆桂英挂帅》，"
				+ "作为国庆十周年献礼节目。1961年8月8日在北京去世。著有《梅兰芳文集》、《梅兰芳演出剧本选》、《舞台生活四十年》等。代表剧目有《贵妃醉酒》、《天女散花》、《宇宙锋》、《打渔杀家》等，"
				+ "先后培养、教授学生100多人。前室王明华，续室福芝芳。女梅葆玥工老生；子梅葆玖继承祖艺工旦行，为梅门四代旦角演员。弟子有（按姓氏笔画）：丁至云、马小曼、马金凤（豫剧）、"
				+ "于素秋、王志怡、王佩瑜、王素琴、王熙春、王慧萍、毛世来、毛剑秋、云燕铭、申丽媛、白玉薇、卢燕、刘元彤、刘肖梅、刘淑华、阎立品（豫剧）、许守义、关肃霜、阳友鹤（川剧）、毕谷云、"
				+ "吕慧君、红线女（粤剧）、任颖华、华慧麟、言慧珠、沈小梅、沈曼华、汪剑耘、李元芳、李世芳、李玉芝、李玉芙、李玉茹、李吟香、李丽、李金鸿、李国粹、李香匀、李砚秀、李桂云（梆子）、"
				+ "李湘君、李斐叔、李蔷华、李毓芳、李慧芳、李慧琴、李碧慧、李薇华、李燕香、杜近芳、杜丽云、杨玉娟、杨荣环、杨秋玲、杨维君、杨慧敏、杨畹侬、张南云、张世孝、张君秋、张春秋、张志英、"
				+ "张曼玲、张淑娴、张蝶芬、张丽娟、陈书舫（川剧）、陈永玲、陈正薇、陈伯华（汉剧）、吴若兰、邹慧兰、范玉媛、罗惠兰、周曼如、高玉倩、高华、徐东来、徐再蓉、徐碧云、梁小鸾、唐富尧、赵文漪、"
				+ "赵慧娟、胡蝶、胡芝风、胡漱芳、南铁生、陶默庵、郭建英、海碧霞、贾世珍、顾正秋、顾景梅、韩淑华、秦慧芬、章遏云、黄世恩、曹慧麟、崔秀茹、童芷苓、谢虹雯、谢黛琳、喻志清、舒昌玉、程砚秋、焦鸿英、"
				+ "新凤霞（评剧）、新艳秋、醉丽君、冀韵兰、魏莲芳，共115人。其中，早期弟子有徐碧云、程砚秋、李斐叔、魏莲芳及新艳秋等。";
		name4 = "梅兰芳";
		
		
		
		content5 = "程砚秋，男，京剧旦角。原名承麟，满族。后改为汉姓程，初名程菊农，后改艳秋，字玉霜。1932年起更名砚秋，改字御霜。幼年家到中落，六岁投荣蝶仙门下，练武功，"
				+ "向荣春亮习武生。一年后向名武生教师丁永利学戏，后因扮相秀丽，改从陈桐云习花旦，后发现嗓音极佳，改学青衣，师从陈啸云。程砚秋童年基本功训练异常艰苦，"
				+ "他以惊人的毅力接受了这些训练，熬过了他惨痛的童年。十一岁登台演出，以其超凡的文武之功，唱、念、做、打崭露头角，行内外耳目一新。在北京丹桂茶园（原东安市场内），"
				+ "与赵桐珊、刘鸿声、孙菊仙等合作演出《桑园寄子》、《辕门斩子》、《朱砂痣》等戏。1917年他因嗓子倒仓，暂不演出，继续深造。他学习绘画、书法、舞拳练剑、观摩电影艺术，"
				+ "大大提高了自己的艺术修养和美学情趣，为日后的艺术创作做了充分的准备。1922年他首次到上海演出，引起轰动，1923年再次到上海，使上海观众欣喜若狂，艺术也逐步趋于成熟。"
				+ "从1925年到1938年，程砚秋步入他风华正茂的黄金时期和“程派”艺术的成熟期，此时程砚秋已经集创作、演出、导演三者于一身，成为较具实力的艺术家。他同时受进步思想的影响，"
				+ "面对广大劳动人民水深火热的社会现实，满腔义愤，编创了许多爱国主义和民主主义思想的剧目，如《文姬归汉》、《荒山泪》、《春闺梦》、《亡蜀鉴》等剧目。"
				+ "在反封建、反军阀内战、反对日本帝国主义侵略战争等不同时期引起观众强烈共鸣，表达了广大群众反对战争、反对压迫、希望和平的强烈愿望。"
				+ "这一时期后他着力于悲剧的表演，继《青霜剑》、《窦娥冤》之后又有《碧玉簪》、《梅妃》及前面提到的一系列悲剧作品的上演，成功的塑造了一批悲剧人物形象，"
				+ "他从此也以擅演悲剧著称。当然，程砚秋也不是光全演悲剧，《锁麟囊》就是他另一类型的代表作。程砚秋在艺术创作上，勇于革新创造，舞台表演唱腔讲究音韵，注重四声，"
				+ "并根据自己独有的嗓音特点，创造出了一种幽咽婉转、若断若续的唱腔风格，形成独有的特点。他创作的角色，典雅娴静，恰如霜天白菊，有一种清峻之美，后成为“四大名旦”之一。"
				+ "程砚秋在表演上无论眼神、身段、步法、指法、水袖、剑术等方面也都有一系列的创造和与众不同的特点，作为一个完整的艺术流派，全面展现在京剧艺术舞台上。"
				+ " 程砚秋注重借鉴兄弟姊妹艺术，融合于自己的艺术创作之中，是众多艺术大师中较为突出的一位。他在大胆革新的前提下，于建国初期编演了他最后一出新戏《英台抗婚》，"
				+ "这出戏无论从唱腔、唱词、舞台表演及美术设计方面都对传统京剧艺术程式做了较大的突破及创新，得到了专家和广大观众的一致肯定。"
				+ "1956年北京电影制片厂又为他拍摄了电影艺术片《荒山泪》，为保留更多的程腔和水袖，影片增加了很多新唱段（吴祖光改编），并摄下了他结合剧情创作的二百多种水袖表演形式。"
				+ "1957年中央人民广播电台请他和杨宝森合录了《武家坡》一剧。晚年的程砚秋致力于教学和总结舞台艺术经验的工作。1949年作为特邀代表，参加全国政协第一届会议，"
				+ "1950年当选全国人大代表，中国戏协理事会主席团委员，1953年任中国戏曲研究院副院长，1957年由周恩来总理介绍加入中国共产党。程砚秋将他的一生全部献给了京剧艺术事业，"
				+ "他所取得的卓越成就，是京剧艺术近百年来所达到的高峰之一，他不仅对京剧旦角同时也对整个京剧、戏曲的发展都产生着深远、重大的影响。学习程派并较有成就的演员有：陈丽芳、"
				+ "章遏云、新艳秋、赵荣琛、侯玉兰、王吟秋、李世济、李蔷华等。1958年3月9日，他的心脏病又因突发性梗塞加剧，仅几分钟便夺去了这位艺术大师的生命，年仅54岁。";
		name5 = "程砚秋";
		
		content6 = "亦受教于路三宝、陆杏林";
		name6 = "王蕙芳";
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test() {
		relationship = extractor.parseAllRelationship(content6,name6);
		System.out.println(relationship);	
	}

	@Test
	public void testAll(){
		relationship = extractor.parseAllRelationship(content1,name1);
		System.out.println(relationship);
		
		relationship = extractor.parseAllRelationship(content2,name2);
		System.out.println(relationship);
		
		relationship = extractor.parseAllRelationship(content3,name3);
		System.out.println(relationship);
		
		relationship = extractor.parseAllRelationship(content4,name4);
		System.out.println(relationship);
		
		relationship = extractor.parseAllRelationship(content5,name5);
		System.out.println(relationship);	
		
		relationship = extractor.parseAllRelationship(content6,name6);
		System.out.println(relationship);	
	}
}
