package com.gpp.api.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

/**
 * A class that represents an immutable universally unique identifier (UUID).
 * A UUID represents a 128-bit value.
 * <p/>
 * <p>There exist different variants of these global identifiers. The methods
 * of this class are for manipulating the Leach-Salz variant, although the
 * constructors allow the creation of any variant of UUID (described below).
 * <p/>
 * <p>The layout of a variant 2 (Leach-Salz) UUID is as follows:
 * <p/>
 * The most significant long consists of the following unsigned fields:
 * <pre>
 * 0xFFFFFFFF00000000 time_low
 * 0x00000000FFFF0000 time_mid
 * 0x000000000000F000 version
 * 0x0000000000000FFF time_hi
 * </pre>
 * The least significant long consists of the following unsigned fields:
 * <pre>
 * 0xC000000000000000 variant
 * 0x3FFF000000000000 clock_seq
 * 0x0000FFFFFFFFFFFF node
 * </pre>
 * <p/>
 * <p>The variant field contains a value which identifies the layout of
 * the <tt>UUID</tt>. The bit layout described above is valid only for
 * a <tt>UUID</tt> with a variant value of 2, which indicates the
 * Leach-Salz variant.
 * <p/>
 * <p>The version field holds a value that describes the type of this
 * <tt>UUID</tt>. There are four different basic types of UUIDs: time-based,
 * DCE security, name-based, and randomly generated UUIDs. These types
 * have a version value of 1, 2, 3 and 4, respectively.
 * <p/>
 * <p>For more information including algorithms used to create <tt>UUID</tt>s,
 * see the Internet-Draft <a href="http://www.ietf.org/internet-drafts/draft-mealling-uuid-urn-03.txt">UUIDs and GUIDs</a>
 * or the standards body definition at
 * <a href="http://www.iso.ch/cate/d2229.html">ISO/IEC 11578:1996</a>.
 *
 * @version 1.14, 07/12/04
 * @since 1.5
 */
public final class UUID implements java.io.Serializable {

    /**
     * Explicit serialVersionUID for interoperability.
     */
    private static final long serialVersionUID = -4856846361193249489L;
    /*
     * The random number generator used by this class to create random
     * based UUIDs.
     */
    private static volatile SecureRandom numberGenerator = null;
    /*
     * The most significant 64 bits of this UUID.
     *
     * @serial
     */
    private final long mostSigBits;
    /**
     * The least significant 64 bits of this UUID.
     *
     * @serial
     */
    private final long leastSigBits;
    /*
     * The version number associated with this UUID. Computed on demand.
     */
    private transient int version = -1;
    /*
     * The variant number associated with this UUID. Computed on demand.
     */
    private transient int variant = -1;
    /*
     * The timestamp associated with this UUID. Computed on demand.
     */
    private transient volatile long timestamp = -1;
    /*
     * The clock sequence associated with this UUID. Computed on demand.
     */
    private transient int sequence = -1;
    /*
     * The node number associated with this UUID. Computed on demand.
     */
    private transient long node = -1;
    /*
     * The hashcode of this UUID. Computed on demand.
     */
    private transient int hashCode = -1;

    // Constructors and Factories

    /*
     * Private constructor which uses a byte array to construct the new UUID.
     */
    private UUID(byte[] data) {
        long msb = 0;
        long lsb = 0;
        for (int i = 0; i < 8; i++)
            msb = (msb << 8) | (data[i] & 0xff);
        for (int i = 8; i < 16; i++)
            lsb = (lsb << 8) | (data[i] & 0xff);
        this.mostSigBits = msb;
        this.leastSigBits = lsb;
    }

    /**
     * Constructs a new <tt>UUID</tt> using the specified data.
     * <tt>mostSigBits</tt> is used for the most significant 64 bits
     * of the <tt>UUID</tt> and <tt>leastSigBits</tt> becomes the
     * least significant 64 bits of the <tt>UUID</tt>.
     *
     * @param mostSigBits
     * @param leastSigBits
     */
    public UUID(long mostSigBits, long leastSigBits) {
        this.mostSigBits = mostSigBits;
        this.leastSigBits = leastSigBits;
    }

    /**
     * Static factory to retrieve a type 4 (pseudo randomly generated) UUID.
     * <p/>
     * The <code>UUID</code> is generated using a cryptographically strong
     * pseudo random number generator.
     *
     * @return a randomly generated <tt>UUID</tt>.
     */
    @SuppressWarnings("unused")
    public static UUID randomUUID() {
        SecureRandom ng = numberGenerator;
        if (ng == null) {
            numberGenerator = ng = new SecureRandom();
        }

        byte[] randomBytes = new byte[16];
        ng.nextBytes(randomBytes);
        randomBytes[6] &= 0x0f;  /* clear version        */
        randomBytes[6] |= 0x40;  /* set to version 4     */
        randomBytes[8] &= 0x3f;  /* clear variant        */
        randomBytes[8] |= 0x80;  /* set to IETF variant  */
        UUID result = new UUID(randomBytes);
        return new UUID(randomBytes);
    }

    /**
     * Static factory to retrieve a type 3 (name based) <tt>UUID</tt> based on
     * the specified byte array.
     *
     * @param name a byte array to be used to construct a <tt>UUID</tt>.
     * @return a <tt>UUID</tt> generated from the specified array.
     */
    public static UUID nameUUIDFromBytes(byte[] name) {
        MessageDigest md;
        try {
            md = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException nsae) {
            throw new InternalError("MD5 not supported");
        }
        byte[] md5Bytes = md.digest(name);
        md5Bytes[6] &= 0x0f;  /* clear version        */
        md5Bytes[6] |= 0x30;  /* set to version 3     */
        md5Bytes[8] &= 0x3f;  /* clear variant        */
        md5Bytes[8] |= 0x80;  /* set to IETF variant  */
        return new UUID(md5Bytes);
    }

    /**
     * Creates a <tt>UUID</tt> from the string standard representation as
     * described in the {@link #toString} method.
     *
     * @param name a string that specifies a <tt>UUID</tt>.
     * @return a <tt>UUID</tt> with the specified value.
     * @throws IllegalArgumentException if name does not conform to the
     *                                  string representation as described in {@link #toString}.
     */
    public static UUID fromString(String name) {
        String[] components = name.split("-");
        if (components.length != 5)
            throw new IllegalArgumentException("Invalid UUID string: " + name);
        for (int i = 0; i < 5; i++)
            components[i] = "0x" + components[i];

        long mostSigBits = Long.decode(components[0]).longValue();
        mostSigBits <<= 16;
        mostSigBits |= Long.decode(components[1]).longValue();
        mostSigBits <<= 16;
        mostSigBits |= Long.decode(components[2]).longValue();

        long leastSigBits = Long.decode(components[3]).longValue();
        leastSigBits <<= 48;
        leastSigBits |= Long.decode(components[4]).longValue();

        return new UUID(mostSigBits, leastSigBits);
    }

    // Field Accessor Methods

    /**
     * Returns val represented by the specified number of hex digits.
     */
    private static String digits(long val, int digits) {
        long hi = 1L << (digits * 4);
        return Long.toHexString(hi | (val & (hi - 1))).substring(1);
    }

    private static void getName() {
        String name = "";
        for (int i = 0; i < 100; i++) {
            String[] firsname = {"赵", "钱", "孙", "李", "周", "吴", "郑", "王", "冯", "陈",
                    "楮", "卫", "蒋", "沈", "韩", "杨", "朱", "秦", "尤", "许", "何", "吕",
                    "施", "张", "孔", "曹", "严", "华", "金", "魏", "陶", "姜", "戚", "谢",
                    "邹", "喻", "柏", "水", "窦", "章", "云", "苏", "潘", "葛", "奚", "范",
                    "彭", "郎", "鲁", "韦", "昌", "马", "苗", "凤", "花", "方", "俞", "任",
                    "袁", "柳", "酆", "鲍", "史", "唐", "费", "廉", "岑", "薛", "雷", "贺",
                    "倪", "汤", "滕", "殷", "罗", "毕", "郝", "邬", "安", "常", "乐", "于",
                    "时", "傅", "皮", "卞", "齐", "康", "伍", "余", "元", "卜", "顾", "孟",
                    "平", "黄", "和", "穆", "萧", "尹", "姚", "邵", "湛", "汪", "祁", "毛",
                    "禹", "狄", "米", "贝", "明", "臧", "计", "伏", "成", "戴", "谈", "宋",
                    "茅", "庞", "熊", "纪", "舒", "屈", "项", "祝", "董", "梁", "杜", "阮",
                    "蓝", "闽", "席", "季", "麻", "强", "贾", "路", "娄", "危", "江", "童",
                    "颜", "郭", "梅", "盛", "林", "刁", "锺", "徐", "丘", "骆", "高", "夏",
                    "蔡", "田", "樊", "胡", "凌", "霍", "虞", "万", "支", "柯", "昝", "管",
                    "卢", "莫", "经", "房", "裘", "缪", "干", "解", "应", "宗", "丁", "宣",
                    "贲", "邓", "郁", "单", "杭", "洪", "包", "诸", "左", "石", "崔", "吉",
                    "钮", "龚", "程", "嵇", "邢", "滑", "裴", "陆", "荣", "翁", "荀", "羊",
                    "於", "惠", "甄", "麹", "家", "封", "芮", "羿", "储", "靳", "汲", "邴",
                    "糜", "松", "井", "段", "富", "巫", "乌", "焦", "巴", "弓", "牧", "隗",
                    "山", "谷", "车", "侯", "宓", "蓬", "全", "郗", "班", "仰", "秋", "仲",
                    "伊", "宫", "宁", "仇", "栾", "暴", "甘", "斜", "厉", "戎", "祖", "武",
                    "符", "刘", "景", "詹", "束", "龙", "叶", "幸", "司", "韶", "郜", "黎",
                    "蓟", "薄", "印", "宿", "白", "怀", "蒲", "邰", "从", "鄂", "索", "咸",
                    "籍", "赖", "卓", "蔺", "屠", "蒙", "池", "乔", "阴", "郁", "胥", "能",
                    "苍", "双", "闻", "莘", "党", "翟", "谭", "贡", "劳", "逄", "姬", "申",
                    "扶", "堵", "冉", "宰", "郦", "雍", "郤", "璩", "桑", "桂", "濮", "牛",
                    "寿", "通", "边", "扈", "燕", "冀", "郏", "浦", "尚", "农", "温", "别",
                    "庄", "晏", "柴", "瞿", "阎", "充", "慕", "连", "茹", "习", "宦", "艾",
                    "鱼", "容", "向", "古", "易", "慎", "戈", "廖", "庾", "终", "暨", "居",
                    "衡", "步", "都", "耿", "满", "弘", "匡", "国", "文", "寇", "广", "禄",
                    "阙", "东", "欧", "殳", "沃", "利", "蔚", "越", "夔", "隆", "师", "巩",
                    "厍", "聂", "晁", "勾", "敖", "融", "冷", "訾", "辛", "阚", "那", "简",
                    "饶", "空", "曾", "毋", "沙", "乜", "养", "鞠", "须", "丰", "巢", "关",
                    "蒯", "相", "查", "后", "荆", "红", "游", "竺", "权", "逑", "盖", "益",
                    "桓", "公", "万俟", "司马", "上官", "欧阳", "夏侯", "诸葛", "闻人", "东方", "赫连",
                    "皇甫", "尉迟", "公羊", "澹台", "公冶", "宗政", "濮阳", "淳于", "单于", "太叔",
                    "申屠", "公孙", "仲孙", "轩辕", "令狐", "锺离", "宇文", "长孙", "慕容", "鲜于",
                    "闾丘", "司徒", "司空", "丌官", "司寇", "仉", "督", "子车", "颛孙", "端木", "巫马",
                    "公西", "漆雕", "乐正", "壤驷", "公良", "拓拔", "夹谷", "宰父", "谷梁", "晋", "楚",
                    "阎", "法", "汝", "鄢", "涂", "钦", "段干", "百里", "东郭", "南门", "呼延",
                    "归", "海", "羊舌", "微生", "岳", "帅", "缑", "亢", "况", "后", "有", "琴",
                    "梁丘", "左丘", "东门", "西门", "商", "牟", "佘", "佴", "伯", "赏", "南宫",
                    "墨", "哈", "谯", "笪", "年", "爱", "阳", "佟"};
            String[] namelist = {"伟", "伟", "芳", "伟", "秀英", "秀英", "娜", "秀英", "伟",
                    "敏", "静", "丽", "静", "丽", "强", "静", "敏", "敏", "磊", "军", "洋",
                    "勇", "勇", "艳", "杰", "磊", "强", "军", "杰", "娟", "艳", "涛", "涛",
                    "明", "艳", "超", "勇", "娟", "杰", "秀兰", "霞", "敏", "军", "丽", "强",
                    "平", "刚", "杰", "桂英", "芳", "　嘉懿", "煜城", "懿轩", "烨伟", "苑博", "伟泽",
                    "熠彤", "鸿煊", "博涛", "烨霖", "烨华", "煜祺", "智宸", "正豪", "昊然", "明杰",
                    "立诚", "立轩", "立辉", "峻熙", "弘文", "熠彤", "鸿煊", "烨霖", "哲瀚", "鑫鹏",
                    "致远", "俊驰", "雨泽", "烨磊", "晟睿", "天佑", "文昊", "修洁", "黎昕", "远航",
                    "旭尧", "鸿涛", "伟祺", "荣轩", "越泽", "浩宇", "瑾瑜", "皓轩", "擎苍", "擎宇",
                    "志泽", "睿渊", "楷瑞", "子轩", "弘文", "哲瀚", "雨泽", "鑫磊", "修杰", "伟诚",
                    "建辉", "晋鹏", "天磊", "绍辉", "泽洋", "明轩", "健柏", "鹏煊", "昊强", "伟宸",
                    "博超", "君浩", "子骞", "明辉", "鹏涛", "炎彬", "鹤轩", "越彬", "风华", "靖琪",
                    "明诚", "高格", "光华", "国源", "冠宇", "晗昱", "涵润", "翰飞", "翰海", "昊乾",
                    "浩博", "和安", "弘博", "宏恺", "鸿朗", "华奥", "华灿", "嘉慕", "坚秉", "建明",
                    "金鑫", "锦程", "瑾瑜", "晋鹏", "经赋", "景同", "靖琪", "君昊", "俊明", "季同",
                    "开济", "凯安", "康成", "乐语", "力勤", "良哲", "理群", "茂彦", "敏博", "明达",
                    "朋义", "彭泽", "鹏举", "濮存", "溥心", "璞瑜", "浦泽", "奇邃", "祺祥", "荣轩",
                    "锐达", "睿慈", "绍祺", "圣杰", "晟睿", "思源", "斯年", "泰宁", "天佑", "同巍",
                    "奕伟", "祺温", "文虹", "向笛", "心远", "欣德", "新翰", "兴言", "星阑", "修为",
                    "旭尧", "炫明", "学真", "雪风", "雅昶", "阳曦", "烨熠", "英韶", "永贞", "咏德",
                    "宇寰", "雨泽", "玉韵", "越彬", "蕴和", "哲彦", "振海", "正志", "子晋", "自怡",
                    "德赫", "君平"};
            int a = (int) Math.abs(firsname.length * Math.random());
            int b = (int) Math.abs(namelist.length * Math.random());
            name = firsname[a] + namelist[b];
            //System.out.println(name);
        }
        System.out.println(name);
    }

    //最终生成要用的uuid的方法
    public static String getUUID() {
        return UUID.randomUUID().toString().replace("-", "");
    }

    public static void main(String[] args) {
        System.out.println("格式前的UUID ： " + UUID.randomUUID().toString());
        System.out.println("格式化后的UUID ：" + getUUID());

        getName();

       /*String[] id = new String[2];
       id[0]="1";
       id[1]="2";
       String[] ids = id.split(",");
       System.out.println(ids.toString());*/
    }

    /**
     * Returns the least significant 64 bits of this UUID's 128 bit value.
     *
     * @return the least significant 64 bits of this UUID's 128 bit value.
     */
    public long getLeastSignificantBits() {
        return leastSigBits;
    }

    /**
     * Returns the most significant 64 bits of this UUID's 128 bit value.
     *
     * @return the most significant 64 bits of this UUID's 128 bit value.
     */
    public long getMostSignificantBits() {
        return mostSigBits;
    }

    /**
     * The version number associated with this <tt>UUID</tt>. The version
     * number describes how this <tt>UUID</tt> was generated.
     * <p/>
     * The version number has the following meaning:<p>
     * <ul>
     * <li>1    Time-based UUID
     * <li>2    DCE security UUID
     * <li>3    Name-based UUID
     * <li>4    Randomly generated UUID
     * </ul>
     *
     * @return the version number of this <tt>UUID</tt>.
     */
    public int version() {
        if (version < 0) {
            // Version is bits masked by 0x000000000000F000 in MS long
            version = (int) ((mostSigBits >> 12) & 0x0f);
        }
        return version;
    }

    // Object Inherited Methods

    /**
     * The variant number associated with this <tt>UUID</tt>. The variant
     * number describes the layout of the <tt>UUID</tt>.
     * <p/>
     * The variant number has the following meaning:<p>
     * <ul>
     * <li>0    Reserved for NCS backward compatibility
     * <li>2    The Leach-Salz variant (used by this class)
     * <li>6    Reserved, Microsoft Corporation backward compatibility
     * <li>7    Reserved for future definition
     * </ul>
     *
     * @return the variant number of this <tt>UUID</tt>.
     */
    public int variant() {
        if (variant < 0) {
            // This field is composed of a varying number of bits
            if ((leastSigBits >>> 63) == 0) {
                variant = 0;
            } else if ((leastSigBits >>> 62) == 2) {
                variant = 2;
            } else {
                variant = (int) (leastSigBits >>> 61);
            }
        }
        return variant;
    }

    /**
     * The timestamp value associated with this UUID.
     * <p/>
     * <p>The 60 bit timestamp value is constructed from the time_low,
     * time_mid, and time_hi fields of this <tt>UUID</tt>. The resulting
     * timestamp is measured in 100-nanosecond units since midnight,
     * October 15, 1582 UTC.<p>
     * <p/>
     * The timestamp value is only meaningful in a time-based UUID, which
     * has version type 1. If this <tt>UUID</tt> is not a time-based UUID then
     * this method throws UnsupportedOperationException.
     *
     * @throws UnsupportedOperationException if this UUID is not a
     *                                       version 1 UUID.
     */
    public long timestamp() {
        if (version() != 1) {
            throw new UnsupportedOperationException("Not a time-based UUID");
        }
        long result = timestamp;
        if (result < 0) {
            result = (mostSigBits & 0x0000000000000FFFL) << 48;
            result |= ((mostSigBits >> 16) & 0xFFFFL) << 32;
            result |= mostSigBits >>> 32;
            timestamp = result;
        }
        return result;
    }

    /**
     * The clock sequence value associated with this UUID.
     * <p/>
     * <p>The 14 bit clock sequence value is constructed from the clock
     * sequence field of this UUID. The clock sequence field is used to
     * guarantee temporal uniqueness in a time-based UUID.<p>
     * <p/>
     * The  clockSequence value is only meaningful in a time-based UUID, which
     * has version type 1. If this UUID is not a time-based UUID then
     * this method throws UnsupportedOperationException.
     *
     * @return the clock sequence of this <tt>UUID</tt>.
     * @throws UnsupportedOperationException if this UUID is not a
     *                                       version 1 UUID.
     */
    public int clockSequence() {
        if (version() != 1) {
            throw new UnsupportedOperationException("Not a time-based UUID");
        }
        if (sequence < 0) {
            sequence = (int) ((leastSigBits & 0x3FFF000000000000L) >>> 48);
        }
        return sequence;
    }

    /**
     * The node value associated with this UUID.
     * <p/>
     * <p>The 48 bit node value is constructed from the node field of
     * this UUID. This field is intended to hold the IEEE 802 address
     * of the machine that generated this UUID to guarantee spatial
     * uniqueness.<p>
     * <p/>
     * The node value is only meaningful in a time-based UUID, which
     * has version type 1. If this UUID is not a time-based UUID then
     * this method throws UnsupportedOperationException.
     *
     * @return the node value of this <tt>UUID</tt>.
     * @throws UnsupportedOperationException if this UUID is not a
     *                                       version 1 UUID.
     */
    public long node() {
        if (version() != 1) {
            throw new UnsupportedOperationException("Not a time-based UUID");
        }
        if (node < 0) {
            node = leastSigBits & 0x0000FFFFFFFFFFFFL;
        }
        return node;
    }

    // Comparison Operations

    /**
     * Returns a <code>String</code> object representing this
     * <code>UUID</code>.
     * <p/>
     * <p>The UUID string representation is as described by this BNF :
     * <pre>
     *  UUID                   = <time_low> "-" <time_mid> "-"
     *                           <time_high_and_version> "-"
     *                           <variant_and_sequence> "-"
     *                           <node>
     *  time_low               = 4*<hexOctet>
     *  time_mid               = 2*<hexOctet>
     *  time_high_and_version  = 2*<hexOctet>
     *  variant_and_sequence   = 2*<hexOctet>
     *  node                   = 6*<hexOctet>
     *  hexOctet               = <hexDigit><hexDigit>
     *  hexDigit               =
     *        "0" | "1" | "2" | "3" | "4" | "5" | "6" | "7" | "8" | "9"
     *        | "a" | "b" | "c" | "d" | "e" | "f"
     *        | "A" | "B" | "C" | "D" | "E" | "F"
     * </pre>
     *
     * @return a string representation of this <tt>UUID</tt>.
     */
    public String toString() {
        return (digits(mostSigBits >> 32, 8) + "-" +
                digits(mostSigBits >> 16, 4) + "-" +
                digits(mostSigBits, 4) + "-" +
                digits(leastSigBits >> 48, 4) + "-" +
                digits(leastSigBits, 12));
    }

    /**
     * Returns a hash code for this <code>UUID</code>.
     *
     * @return a hash code value for this <tt>UUID</tt>.
     */
    public int hashCode() {
        if (hashCode == -1) {
            hashCode = (int) ((mostSigBits >> 32) ^
                    mostSigBits ^
                    (leastSigBits >> 32) ^
                    leastSigBits);
        }
        return hashCode;
    }

    /**
     * Compares this object to the specified object.  The result is
     * <tt>true</tt> if and only if the argument is not
     * <tt>null</tt>, is a <tt>UUID</tt> object, has the same variant,
     * and contains the same value, bit for bit, as this <tt>UUID</tt>.
     *
     * @param obj the object to compare with.
     * @return <code>true</code> if the objects are the same;
     * <code>false</code> otherwise.
     */
    public boolean equals(Object obj) {
        if (!(obj instanceof UUID))
            return false;
        if (((UUID) obj).variant() != this.variant())
            return false;
        UUID id = (UUID) obj;
        return (mostSigBits == id.mostSigBits &&
                leastSigBits == id.leastSigBits);
    }

    /**
     * Compares this UUID with the specified UUID.
     * <p/>
     * <p>The first of two UUIDs follows the second if the most significant
     * field in which the UUIDs differ is greater for the first UUID.
     *
     * @param val <tt>UUID</tt> to which this <tt>UUID</tt> is to be compared.
     * @return -1, 0 or 1 as this <tt>UUID</tt> is less than, equal
     * to, or greater than <tt>val</tt>.
     */
    public int compareTo(UUID val) {
        // The ordering is intentionally set up so that the UUIDs
        // can simply be numerically compared as two numbers
        return (this.mostSigBits < val.mostSigBits ? -1 :
                (this.mostSigBits > val.mostSigBits ? 1 :
                        (this.leastSigBits < val.leastSigBits ? -1 :
                                (this.leastSigBits > val.leastSigBits ? 1 :
                                        0))));
    }

    /**
     * Reconstitute the <tt>UUID</tt> instance from a stream (that is,
     * deserialize it). This is necessary to set the transient fields
     * to their correct uninitialized value so they will be recomputed
     * on demand.
     */
    private void readObject(java.io.ObjectInputStream in)
            throws java.io.IOException, ClassNotFoundException {

        in.defaultReadObject();

        // Set "cached computation" fields to their initial values
        version = -1;
        variant = -1;
        timestamp = -1;
        sequence = -1;
        node = -1;
        hashCode = -1;
    }
}

