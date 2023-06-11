package com.example.myapplication;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private DataHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //初始化数据库
        dbHelper = new DataHelper(this, "Database.db", null, 1);

        //绑定两个输入框
        EditText account = findViewById(R.id.account);
        EditText password = findViewById(R.id.password);

        //注册按钮绑定+点击事件
        View enroll = findViewById(R.id.enroll);
        enroll.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, enroll_Activity.class);
            startActivity(intent);
        });

        boolean inited = PreferenceUtils.get(this).getBoolean("inited", false);
        if (!inited) {
            //如果是false的话就把新闻数据插入数据库
            insertData();
            PreferenceUtils.get(this).putBoolean("inited", true);

        }

        //登录按钮点击事件
        Button login = findViewById(R.id.login);
        login.setOnClickListener(view -> {
            //链接数据库，获取输入框的账户和密码
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            String account1 = account.getText().toString();
            String password1 = password.getText().toString();
            //用于存储数据库的账号密码
            long userId = 0;
            String account2 = "";
            String password2 = "";
            Cursor cursor;
            //查询的字段
            String[] columns = {"id", "account", "password"};
            // 定义查询条件
            String selection = "account=?";
            // 定义查询条件参数
            String[] selectionArgs = {account1};

            //首先判断账号是否为空，不为空就查询数据库
            if (!account1.isEmpty()) {
                //链接数据库
                cursor = db.query("User", columns, selection, selectionArgs, null, null, null);

                //遍历获取Cursor对象拿到账号和密码
                if (cursor.moveToFirst()) {
                    do {
                        userId = cursor.getLong(cursor.getColumnIndexOrThrow("id"));
                        account2 = cursor.getString(cursor.getColumnIndexOrThrow("account"));
                        password2 = cursor.getString(cursor.getColumnIndexOrThrow("password"));
                    } while (cursor.moveToNext());
                }
                //关闭cursor对象
                cursor.close();

                //现在判断输入的账号密码是否能与数据库保存的账号密码匹配，如果是的话进入主页面
                if (account1.equals(account2) && password1.equals(password2)) {


                    // 将当期用户id保存到shared preference中
                    PreferenceUtils.get(this).putLong("current_user", userId);

                    Intent intent = new Intent(MainActivity.this, home_page.class);
                    intent.putExtra("user_name", account1);
                    startActivity(intent);
                    Toast.makeText(MainActivity.this, "欢迎您 用户: " + account1 + " 登录成功", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.this, "登陆失败,请检查账号密码是否出错,没有注册请先注册", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(MainActivity.this, "请输入账号密码", Toast.LENGTH_SHORT).show();
            }
        });


        View forget = findViewById(R.id.forget);
        forget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                SQLiteDatabase db = dbHelper.getWritableDatabase();
                ContentValues contentValues = new ContentValues();
                contentValues.put("password", "123456");
                db.update("User", contentValues, null, null);
                Toast.makeText(MainActivity.this, "密码已重置为123456", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void insertData() {
        // 创建数据库连接对象
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        // 开始事务
        db.beginTransaction();
        try {
            shouye(db);
            shequ(db);
            game(db);
            // 提交事务
            db.setTransactionSuccessful();
        } catch (Exception e) {
            // 回滚事务
            Log.e(TAG, "Error while inserting data into table: " + e.getMessage());
        } finally {
            // 结束事务
            db.endTransaction();
            // 关闭数据库连接
            db.close();
        }
    }

    private void shouye(SQLiteDatabase db) {
        shouyeData(db, "shouye", "EDG冠军教练世一汤 Kenzhu朱开正式回归", R.drawable.gn_1, "今日EDG官方宣布S11的冠军教练Kenzhu正式回归，担任教练一职。上海EDG合创汽车英雄联盟分部人员变动公告\n" + "今天我们很高兴地和大家宣布：KenZhu(朱开)正式回归上海EDG合创汽车英雄联盟分部担任教练一职!\n" + "经历了短暂的分别，又迎来了久违的重逢。无须赘述，时光已然镌刻下了属于我们的共同回忆，非常高兴我们曾经携手踏至巅峰，同时也非常欣喜我们能够再度相逢，一同迎接全新的挑战。相信再次重逢后，我们的配合将会更加默契，我们步伐能够更加坚定，未来的画卷已经展开，全新的故事由我们续写!朱开教练，欢迎回来！");
        shouyeData(db, "shouye", "英雄联盟电竞赛事2023年更新：全球总决赛将在韩国举行", R.drawable.gn_2, "2023英雄联盟季中冠军赛将在英国伦敦举行，比赛时间为当地时间5月2日-5月21日。参赛队伍从十一支增加到十三支，而LPL赛区、LCK赛区、LEC赛区及LCS赛区都将首次拥有两支参赛队伍。赛制上也首次全程使用双败淘汰赛。\n" + "　　季中冠军赛是英雄联盟中最重要的国际赛事之一，自2015年举办以来，中国LPL赛区便拿下四次冠军，RNG战队更是在2018年、2021年及2022年力克韩国LCK赛区队伍，成为全球首个三次夺得季中冠军赛冠军的战队。\n" + "作为该项目一年中含金量及竞技水平最高的比赛，2023英雄联盟全球总决赛将在时隔五年后再次在韩国举行。\n");
        shouyeData(db, "shouye", "《英雄联盟》S12半决赛DRX 3:1 GEN 与T1会师总决赛 ", R.drawable.gn_3, "在今天结束的《英雄联盟》S12半决赛上，DRX 3：1 GEN，让一追三成功，与同样来自LCK的T1成功会师决赛。\n" + "　在决胜局，DRX辅助布隆给GEN造成了很大的困难，后期小龙团，DRX拿下小龙后团灭了GEN，并拿下了大龙。最后带着大龙buff的DRX稳健推进，拿下比赛。\n");
        shouyeData(db, "shouye", "后末日战术冒险游戏《迷瘴纪事》定档5月23日正式发售", R.drawable.gn_4, "由505 Games发行与《突变元年：伊甸园之路》开发商The Bearded Ladies在今天共同宣布，后末日战术冒险游戏《迷瘴纪事》将于2023年5 月23日登陆PC（Steam以及Epic），PlayStation 5，Xbox Series X|S 平台。\n");
        shouyeData(db, "shouye", "现在就能玩！《元气骑士》衍生新作《元气对战》曝光", R.drawable.gn_5, "《元气骑士》衍生游戏《元气对战》于今日曝光，并于3月22日12:00-3月24日12:00进行安卓版试玩。\n");
        shouyeData(db, "shouye", "《和平精英》四周年新版本上线：乘上“冒险列车”，开启竞技冒险新旅程！", R.drawable.gn_6, "《和平精英》即将迎来四周年庆典，为了庆祝这个重要时刻，游戏将上线全新版本“冒险列车”，为特种兵们带来更加丰富多彩的游戏体验。\n" + "在本次新版本中，特种兵将有机会乘坐“四周年和平号”列车在海岛车站之间展开冒险。全新场景中心车站位于海岛的中央位置，每隔一段时间就会发出“四周年和平号”列车前往海岛的各个分车站。特种兵们可以在中心车站等待列车到达，并快速穿梭于中心车站和分车站之间，实现快速转移。\n");
        shouyeData(db, "shouye", "龙骑士，欢迎来到《龙之灵域》！魔幻大世界怪物图鉴大揭秘！", R.drawable.gn_7, "以龙之名，护吾之地。网易自研的史诗大世界魔幻冒险MMORPG手游《龙之灵域》将在3月30日正式迎来“龙灵觉醒”测试！辽阔震撼的康纳图斯世界大门即将开启，诸位龙骑士，准备好参与这次充满惊险与刺激的冒险之旅了吗？\n" + "在这里，你可以尽情沉浸在酣畅激爽的战斗之中，“种族+武器的自由组合”的新颖设定，让龙骑士们实现武器随心切换、技能自由搭配！挣脱一切枷锁，只为淋漓尽致的对决体验！如今康纳图斯世界已经陷入黑暗，为了保卫光明，勇敢迎击邪恶的黑暗势力吧！\n");
        shouyeData(db, "shouye", "《守望先锋2》阳春小鱿活动宣传片公布 今日开启持续至4月5日", R.drawable.gn_8, "今日《守望先锋2》阳春小鱿活动回归游戏，官方公开了上线宣传视频。活动期间登录游戏完成挑战，将获得以小鱿为主题的造型物品奖励，包括小鱿头像、小鱿名牌、全新武器吊坠以及路霸的小鱿皮肤。该活动将持续至4月5日。\n");
        shouyeData(db, "shouye", "暴雪回应称《暗黑4》公开Beta测试时 游戏将运行顺畅不会出现排队问题", R.drawable.gn_9, "今日已经报道过，《暗黑破坏神4》在3月25日将正式开启公开Beta测试，提前购买的玩家可于3月18日抢先体验。但是许多玩家在抢先体验测试时，遇到排队困难问题。现在现在值得关注的问题是，《暗黑4》公开Beta测试和正式发售时，服务器能否抗住压力。\n");
        shouyeData(db, "shouye", "《齐天大战神》终于首发", R.drawable.gn_10, "目前开放四个职业之中，猴子高攻高暴、老牛血厚AOE、精精专攻法术、哪吒主打一个快字。但其实通过后天修炼aka加点，选谁都能build出自己想要的战斗效果，真真是大道三千殊途同归。\n");
        shouyeData(db, "shouye", "《FIFA23》一款质量堪忧的游戏与EA的几大罪行", R.drawable.gn_11,"2022年对于球迷来说是不一样的一年，从固定的夏日变成了冬季，足球作为团队运动，每支球队首发11人都会拼尽全力取得胜利，世界杯作为足球比赛最高的舞台，吸引了一批又有一批年轻人加入足球球迷的大家庭，年轻人嘛喜欢玩游戏，但是面对《FM》（足球经理）系列的上手门槛，更多人都会选择直接可以操控的《FIFA》，更加的快捷方便的上手，体验自己喜爱的球队或是球员，这也让FIFA23成为了EA至今系列最畅销的一代，正是如此原因引出了今天的这篇文章，为什么我拒绝向任何人推荐FIFA，并称这是一款质量堪忧游戏，请让我列举EA的几大罪行。如果你是预购玩家，我相信你大概率会和我一样，预购后一直等待到游戏解锁的时候，啪的一下，EAAC出现了，这个由EA自主开发，倾尽全力并想使用在未来所有自家旗下游戏的“反作弊”系统，它完全做到了“反作弊”这个职责，我到现在都记得那天晚上各大游戏论坛都在找解决方案，不出意外我也一样，不过在各种重启后我还是顺利进入游戏了。而后来网上各种解决方法，比如重装EAAC的FIFA23内容，安全模式启动电脑等等方法，EA让玩家成功了解了反作弊到底在反什么与他们的技术力稀烂的事实。当你看到这的时候可能你会觉得这个也没啥，无非花点时间罢了，然而事实上很多人因为这个反作弊的问题游戏时长超过了2小时，无法退款又不能进游戏，只好最后在steam评论一句骂骂咧咧的一肚子气去睡觉。\n" +
                "第二罪行其实是很严重的问题，作为初版FIFA23的球员模型跟脸模问题，我的主队是利物浦，这赛季标王努涅斯不仅没有脸模，甚至是个光头？首先EA与多家俱乐部都有深度合作，在休赛期的时候EA就会去每家俱乐部进行面部采集，那么请问一下你们采集的内容呢？为什么需要后续多次版本更新才把早就做好的内容放出来？每一次都是选择磨洋工拖来拖去这样真的有必要吗？如果说后续更新将脸模补充完毕了还能原谅，直到世界杯时期UT模式更新的世界杯传奇事故让我直接不能接受，1350万PC端第一张购买的世界杯WC大罗当我使用的时候我发现模型完全是错误的，这里跟不了解的朋友说一下FIFA23这款游戏一些球员的模型是特殊制作的，作为顶级卡的大罗自然更是特殊模型，可是游戏中模型却是别的传奇？未免有些抽象了。\n" +
                "EA的第四罪行：为了骗钱努力滚卡的UT模式\n" +
                "在写稿子时现在已经是西甲赛季蓝阶段了，我不止一次说EA为了榨干“FIFA”ip的目的图穷匕见了，由于EA无法与国际足联谈拢ip分成的问题，所以EA决定下一代FIFA系列改名《EAFC24》，既然要改名了，碰巧世界杯的来临，EA很聪明的选择了滚卡压数值的方式，推出更多的活动色卡，让玩家肝代币为他们的服务器打工，从世界杯传奇到年度蓝传奇，甚至今年还把FUT生日色卡中也加入了生日传奇，属于是物尽其用让玩家为了自己喜欢的球员氪金开包抽卡，但是这样换来了什么结果呢？玩家骂声一片，不过EA毫不在意；就拿这两天我一直跟朋友讨论的问题，为什么德容没有赛季蓝？如果不看球的话我完全可以说今年西甲是皇马夺冠了吧，赛季蓝的阵容完完全全就是为了流量为了骗氪而推出的球员卡，这种吃香简直不要太难看。EA的第五罪行：在原则上的问题\n" +
                "农历新年来临时，作为东亚国家大多数都是要进行新年庆祝的，而EA选择了一个让我怎么都没想到的一个更新，任务球衣：“韩国农历新年”，我承认大英博物馆今年也是这主题，但是你EA舔英国人的行为大家早就知道，你也不至于这也要舔吧？就算后面修改了任务标题，结果还是无济于事没人会关心你是否真的知道农历新年是属于谁，我只知道在中国市场有需求的俱乐部录制视频内容全都是“happy chinese new year”。结语\n" +
                "其实我当然知道还有很多问题，比如球员滑冰，外脚背任意球还是那么无敌，dda平衡，还有一些游戏操作让我真的有必要思考我使用的球员真的是职业球员吗等等内容，但是就我个人steam950小时时长，橘子版590小时时长的感受来说，我不会向任何人推荐这款游戏，除非你的时间太多了想给EA打工，以上就是我在《EAFC24》发布前对年货《FIFA》系列最后一代的看法，感谢你的观看。");

    }


    private void shequ(SQLiteDatabase db) {

        shequData(db, "shequ","起飞！Steam统统免费！限时永久入库！《原子之心》免费玩！", "起飞！Steam统统免费！限时永久入库！《原子之心》免费玩！\n" +
                "\n" +
                "steam虾饺\n" +
                "\n" +
                "Lv.10\n" +
                "2天前·江苏\n" +
                "\n" +
                "PC游戏\n" +
                "Steam限时永久入库《地铁：最后的曙光》！\n" +
                "\n" +
                "“地铁三部曲”是4A Games开发的后末日单人FPS游戏系列，其中第二部作品《地铁：最后的曙光》发售于2013年。为了庆祝发售10周年，官方宣布将于5月18日~25日期间开启“Steam喜加一”活动，所有玩家都可以免费领取！\n" +
                "\n" +
                "\n" +
                "\n" +
                "这次送出的将会是《地铁：最后的曙光》原版的完整版本，不是后来新出的Redux版本。不过领取之后将会永久保留。这部游戏Steam好评率89%，不支持中文（打个汉化补丁就行），游戏体验还是挺不错的，感兴趣的社友不要错过哦~\n" +
                "\n" +
                "\n" +
                "\n" +
                "游戏的喜加一会在今日（5.18）的稍晚时候在Steam开启，感兴趣的社友今晚可以自行前往官网搜索。不那么着急的社友也可以等一等，喜加一正式开启后，明天中午社区的推送可能会有领取传送门。\n" +
                "\n" +
                "《原子之心》Steam免费试用版上线！\n" +
                "\n" +
                "科幻FPS游戏《原子之心》在Steam推出了免费试玩Demo，所有玩家都可以免费体验这款游戏了！\n" +
                "\n" +
                "\n" +
                "\n" +
                "Steam免费下载《原子之心》试用版传送门：\n" +
                "https://store.steampowered.com/app/668580/_/\n" +
                "\n" +
                "试用版主要是让玩家体验这个作品的魅力，如果觉得合适的话，可以玩过之后再进行购入。目前游戏的黄金版和高级版则是7折，标准版也开启了75折特惠。\n" +
                "\n" +
                "Steam每日特惠\n" +
                "原子之心 标准版\n" +
                "\n" +
                "\n" +
                "-25% Steam售价：239 折后价：179.25（史低）\n" +
                "截止时间：5月23日\n" +
                "支持简体中文 好评率81%\n" +
                "\n" +
                "【短评】：“游戏的表现力像苏联的艺术品，游戏的底层设计像俄罗斯的经济结构”。玩之前以为这是一款恐怖游戏，而主角只是一个挣扎求生的普通人，完之后才发现主角其实是个战斗力爆表的谐星……\n" +
                "\n" +
                "Steep™ 极限巅峰\n" +
                "\n" +
                "\n" +
                "-85% Steam售价：148 折后价：22.2（新史低）\n" +
                "截止时间：5月23日\n" +
                "支持简体中文 好评率58%\n" +
                "\n" +
                "【短评】：好玩，只是育碧的小土豆服务器太拉了，就不能搞个单机版嘛\n" +
                "\n" +
                "Rust 腐蚀\n" +
                "\n" +
                "\n" +
                "-33% Steam售价：161 折后价：107.87（史低25）\n" +
                "截止时间：5月24日\n" +
                "支持简体中文 好评率87%\n" +
                "\n" +
                "【短评】：再厚的墙，再稳的房，不如兄弟在身旁。（记得千万别在网吧玩这个）\n" +
                "\n" +
                "\n" +
                "免费玩本体！《死亡空间重制版》可在Steam试玩90分钟\n" +
                "\n" +
                "以为就到这里了嘛，哒美，今天接下来也全是免费~\n" +
                "\n" +
                "\n" +
                "Motive Studio广受好评的《死亡空间重制版（Dead Space Remake）》目前在Steam上享受-20%的折扣，同时允许让玩家免费试玩游戏90分钟。\n" +
                "\n" +
                "《死亡空间重制版》Steam免费玩90分钟传送门：\n" +
                "https://store.steampowered.com/app/1693980/_/\n" +
                "\n" +
                "\n" +
                "\n" +
                "Steam一般情况下允许游戏两个小时内退款，部分游戏会有独立的试玩版，但这样直接限时试玩本体游戏却并不多见。玩家可以在限定时间内尽可能多地探索游戏，而不必为退款而烦恼。未来几个月是否会有其他Steam游戏有这样的试玩机会还有待观察。\n" +
                "\n" +
                "\n" +
                "\n" +
                "本次试玩将于5月30日结束，目前尚不知道试玩在该时间点后能否继续游玩。总之，如果您还没有试过《死亡空间重制版》，那么现在就是最好的时机。\n" +
                "\n" +
                "\n" +
                "\n" +
                "《鬼谷八荒》EA结束 正式版5月26日上线限时免费领DLC\n" +
                "\n" +
                "《鬼谷八荒》官宣抢先测试现已结束，将于5月26日在steam平台发布V1.0正式版。正式版上线当日，已拥有《鬼谷八荒》本体的玩家可以在steam商店页限时免费领取DLC《不归玄境》。\n" +
                "\n" +
                "，时长01:59\n" +
                "\n" +
                "DLC具体领取方式请后续关注《鬼谷八荒》steam商店页信息或社区公告。\n" +
                "\n" +
                "《鬼谷八荒》页面传送门：\n" +
                "PC（steam）https://store.steampowered.com/app/1468810/\n" +
                "\n" +
                "\n" +
                "\n" +
                "5月26号领取正式开放后，社区这边的推送应该也会提醒下大家可以去领了，不用担心忘掉而错过。\n" +
                "\n" +
                "\n" +
                "\n" +
                "刀哥亲自祝福！《东北之夏2》现已免费上线Steam\n" +
                "\n" +
                "国产免费视觉小说游戏《东北之夏》开发者庄不纯宣布，DLC内容“东北之夏2”现已免费上线Steam，更新游戏后在主菜单就可直接游玩，刀哥也亲自送来了祝福。\n" +
                "\n" +
                "，时长02:53\n" +
                "\n" +
                "Steam免费下《东北之夏2》传送门：\n" +
                "https://store.steampowered.com/app/2121360/_/\n" +
                "\n" +
                "\n" +
                "\n" +
                "《东北之夏》是一款免费的视觉小说。讲述了主角遵从父母意愿来到沈阳打拼，阴差阳错地结识了两位小网红“虎妞”和“刀酱”后和杀马特团长相爱相杀的故事。\n" +
                "\n" +
                "\n" +
                "\n" +
                "\n" +
                "194\n" +
                "\", \"图片名称（或者图片的链接）\", \"作者名字\", \"作者头像（如果不需要我可以全都用一样的替代）\" );\n" +
                "Data(\"陈睿：超过8成的985、211学生是B站用户 年轻人热爱学习\", \"\n" +
                "热门\n" +
                "陈睿：超过8成的985、211学生是B站用户 年轻人热爱学习\n" +
                "\n" +
                "\n" +
                "伊卡还是在想要羽毛\n" +
                "\n" +
                "Lv.18\n" +
                "1天前·天津\n" +
                "#复活吧我的队友#\n" +
                "\n" +
                "\n" +
                "盒友杂谈\n" +
                "最近，在上海科技传播大会上，B站CEO陈睿表示目前平台的主流用户是大学生，B站用户的受教育程度很高。\n" +
                "据他所称，中国985、211大学生中使用B站的比例超过82%，其中上海交大和复旦大学的B站用户比例超过了90%。\n" +
                "\n" +
                "此外。科技和知识类视频在B站用户的主动搜索中排名第二，人工智能相关视频投稿量也同比增长了86%。\n" +
                "陈睿提到：\n" +
                "这些年轻人爱学习，喜欢看科技类的内容，一年内看科技类题材视频的用户超过2亿人，所以相应的知识和科技内容才能流行起来。\n" +
                "中国正面临人工智能这一科技拐点的到来，B站将为更多科技创作者提供平台，帮助更多年轻人爱上科技、投身科技行业。\n" +
                "\n" +
                "此前央视网曾公布一份数据：在2018年，有将近2000万人在B站学习，相当于2017年高考人数的2倍。B站也曾统计，自2020年2月1日到3月25日，不到2个月的时间，UP们在学习频道里上传了数以万计的学习视频，囊括了方方面面的知识。\n" +
                "在日前举行的上海网络视听内容创作者大会上，哔哩哔哩董事长兼CEO陈睿表示，在B站上播放时长最长的内容是高等数学。\n" +
                "\n" +
                "B站数据显示，截至日前，账号“宋浩老师官方”发布的视频《<高等数学>同济版全程教学视频（宋浩老师）》播放量高达1个亿，弹幕数量为218万条。\n" +
                "根据B站2022年财报显示，截至2022年四季度，B站日均活跃（日活）用户达到9280万，同比大增29%，月度活跃（月活）用户也达到3.26亿，同比增加20%。\n" +
                "#复活吧我的队友#\n" +
                "\n" +
                "打赏作者", R.drawable.shequ1, "游戏小编");
        shequData(db, "shequ","MSI季中赛：LPL队伍会师决赛！BLG 3-1 T1！", "详细内容详细内容详细内容详细内容详细内容详细内容详细内容详细内容详细内容详细内容", R.drawable.gn_2, "作者名字");
        shequData(db, "shequ","网易1.5折甩卖暴雪“分手遗产” 有人狂抢4箱转手卖", "详细内容详细内容详细内容详细内容详细内容详细内容详细内容详细内容详细内容详细内容", R.drawable.gn_3, "作者名字");
        shequData(db, "shequ","DPC中国S级联赛：超哥泉水洗澡，AR让一追二 AR 2-1 XG", "详细内容详细内容详细内容详细内容详细内容详细内容详细内容详细内容详细内容详细内容", R.drawable.gn_4, "作者名字");
        shequData(db, "shequ","女神节后又是520，究竟有多少日子被包装成情人节？", "详细内容详细内容详细内容详细内容详细内容详细内容详细内容详细内容详细内容详细内容", R.drawable.gn_5, "作者名字");
        shequData(db, "shequ","三年之期已到，下一款现象级网游是什么？", "详细内容详细内容详细内容详细内容详细内容详细内容详细内容详细内容详细内容详细内容", R.drawable.gn_6, "作者名字");
        shequData(db, "shequ","韦神试训失败？PeRo和17单日斩获两鸡，4AM与NH互换指挥实现双赢", "详细内容详细内容详细内容详细内容详细内容详细内容详细内容详细内容详细内容详细内容", R.drawable.gn_7, "作者名字");
        shequData(db, "shequ","《GTA6》男演员社交媒体上晒照 和游戏形象一致", "详细内容详细内容详细内容详细内容详细内容详细内容详细内容详细内容详细内容详细内容",R.drawable.gn_8, "作者名字");

    }

    private void game(SQLiteDatabase db) {

        gameData(db, "game","文明6", "《文明6》是Firaxis Games开发，2K Games发行的历史策略回合制游戏，于2016年10月21日发行PC版本，2018年11月16日登陆Switch平台，2019年11月22日发布了XboxOne、PS4版本，为《文明》系列第六部。 [1]\n" +
                "游戏中玩家建立起一个帝国，并接受时间的考验。玩家将创建及带领自己的文明从石器时代迈向信息时代，并成为世界的领导者。在尝试建立起世界上赫赫有名的伟大文明的过程中，玩家将启动战争、实行外交、促进文化，同时正面对抗历史上的众多领袖。\n" +
                "2022年11月16日，游戏发行商2K公开了《文明6》的领袖季票，新增了18位可游玩的领袖，12位为新领袖，6位为现有领袖替换版本，宣布季票将于11月21日正式推出。 [17] 11月22日消息，《文明 6》领袖季票Steam 上线，售价132 元。", R.drawable.game1, "9.3" );
        gameData(db, "game","鹅鸭杀", "《鹅鸭杀》是Gaggle Studios开发的策略休闲游戏，于2021年10月4日在Steam平台上发布。\n" +
                "玩家在游戏中将化身为鹅阵营、鸭子阵营或中立阵营中的一员，并为了让自己所处的阵营获得胜利而与其他玩家展开一场场紧张刺激的推理。 [1]", R.drawable.game2, "9.1" );
        gameData(db, "game","泰坦陨落", "《泰坦陨落》是一款由Respawn Entertainment工作室制作并由美国艺电公司发行的的第一人称射击网络游戏。该游戏于2014年3月正式发行。\n" +
                "游戏主题是机甲和科幻，主要剧情为人类的发展与宇宙其他势力的冲突，游戏使用起源引擎制作，包括繁体中文版本。", R.drawable.game3, "8.3" );
        gameData(db, "game","城市：天际线", "详细内容详细内容详细内容详细内容详细内容详细内容详细内容详细内容详细内容详细内容", R.drawable.gn_1, "7.3");
        gameData(db, "game","绝地求生", "详细内容详细内容详细内容详细内容详细内容详细内容详细内容详细内容详细内容详细内容", R.drawable.gn_2, "9.6" );
        gameData(db, "game","CS：GO", "详细内容详细内容详细内容详细内容详细内容详细内容详细内容详细内容详细内容详细内容", R.drawable.gn_3, "9.1" );
        gameData(db, "game","饥荒", "详细内容详细内容详细内容详细内容详细内容详细内容详细内容详细内容详细内容详细内容", R.drawable.gn_4, "9.9");
        gameData(db, "game","求生之路", "详细内容详细内容详细内容详细内容详细内容详细内容详细内容详细内容详细内容详细内容", R.drawable.gn_5, "8.3");
        gameData(db, "game","艾尔登法环", "详细内容详细内容详细内容详细内容详细内容详细内容详细内容详细内容详细内容详细内容", R.drawable.gn_6, "8.1");
    }


    private void shouyeData(SQLiteDatabase db, String classify, String title,int img_src, String content) {
        ContentValues values = new ContentValues();
        values.put("classify", classify);
        values.put("title", title);
        values.put("img_src", img_src);
        values.put("content", content);
        db.insert("News_Data", null, values);
    }

    private void shequData(SQLiteDatabase db, String classify, String title, String content, int img_src, String zuozhe) {
        ContentValues values = new ContentValues();
        values.put("classify", classify);
        values.put("title", title);
        values.put("img_src", img_src);
        values.put("content", content);
        values.put("zuozhe", zuozhe);
        db.insert("News_Data", null, values);
    }

    private void gameData(SQLiteDatabase db, String classify, String title, String content, int img_src, String pingfen) {
        ContentValues values = new ContentValues();
        values.put("classify", classify);
        values.put("title", title);
        values.put("img_src", img_src);
        values.put("content", content);
        values.put("pingfen", pingfen);
        db.insert("News_Data", null, values);
    }
}