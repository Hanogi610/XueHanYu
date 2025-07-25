package com.example.xuehanyu.core.sample

import com.example.xuehanyu.core.model.Level
import com.example.xuehanyu.core.model.Type
import com.example.xuehanyu.reader.data.model.Book
import com.example.xuehanyu.reader.data.model.Chapter
import java.util.Date

object NovelDummy {
    private val dummyNovels = listOf(
        Book(
            id = 1L,
            title = "兰亭村之旅",
            author = "张小说",
            releaseDate = "2025/07/24",
            lastUpdated = "2025/07/24",
            viewCount = 1250,
            cover = "https://yt3.googleusercontent.com/1-SiAvMx1JnbrQ4kMScL9otmWZtUlzriKuPBbHjrOSKjtknQmxYpO5USJqq83SPoPf2mbYvg9PI=s900-c-k-c0x00ffffff-no-rj",
            bigCover = "https://upload.wikimedia.org/wikipedia/en/3/37/Adventure_Time_-_Title_card.png",
            type = Type.NOVEL,
            level = Level.HSK3,
            description = "一个年轻人去兰亭村旅游的故事。这个故事适合HSK3水平的学生，包含日常生活、旅游和文化的词汇。跟着小明一起发现这个美丽的村子，学习中文，体验中国的传统文化。",
            chapters = listOf(
                Chapter(
                    title = "到了兰亭村",
                    content = """
                        小明是一个二十五岁的年轻人。他住在北京，工作很忙，每天都要加班。他觉得自己需要休息一下，所以他决定去一个安静的地方旅游。

                        他在网上找了很多地方，最后选择了兰亭村。这个村子在南方，风景很美，也有很多有意思的文化活动。

                        星期六早上，小明坐公共汽车去兰亭村。车程很长，要三个小时。他在车上看书、听音乐，也拍了很多照片。

                        到了兰亭村，小明一下车就觉得空气很新鲜，天空很蓝，周围都是绿色的山和树。他深呼吸了一下，笑着说："这里真漂亮！"

                        一个老人走过来，穿着传统的衣服，脸上有很多皱纹，但眼睛很亮。

                        "你好！"老人说，"你是来旅游的吗？"

                        "是的，我叫小明。我第一次来这里。"小明回答。

                        老人点点头，说："欢迎你！这里的人都很友好。你住在哪里？"

                        "我在网上订了一个民宿，叫'山水之家'。"

                        "哦，我知道那个地方。走吧，我带你去。"

                        小明跟着老人走在小路上。路很窄，但很干净。两边有很多花，还有小鸟在唱歌。

                        "你是哪里人？"老人问。

                        "我是北京人。"

                        "北京很大，也很热闹。兰亭村不一样，这里很安静。"

                        "我喜欢安静的地方，也想学习一些中文。"

                        "你中文说得很好啊！"

                        "谢谢，我还在学习。"

                        到了民宿，小明看见一个小院子，院子里有一棵大树，还有几只鸡在走来走去。房子是木头做的，看起来很温暖。

                        一个阿姨出来迎接他，说："欢迎你，小明！房间已经准备好了。"

                        小明放下行李，坐在院子里喝茶。他看着远处的山，心里很平静。他想："这次旅行一定会很特别。"
                    """.trimIndent(),
                    order = 1
                )
            )
        )
    )

    fun getDummyNovels(): List<Book> {
        return dummyNovels
    }
}