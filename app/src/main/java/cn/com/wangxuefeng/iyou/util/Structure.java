package cn.com.wangxuefeng.iyou.util;

public class Structure {
    static StringBuffer commonReqHeader(int way, int mtd) {
        StringBuffer sb = new StringBuffer("GET / HTTP/1.0\r\n");
        // 以下为请求头
        sb.append("Accept: */*\r\n");
        sb.append("User-Agent: BMTPAgent\r\n");
        sb.append("Host: VOD\r\n");
        sb.append("UGT:NETPLAYER\r\n");
        sb.append("WAY:" + way + "\r\n");
        sb.append("MTD:" + mtd + "\r\n");
        return sb;
    }
    static String mgc_hash(long pid, long sid) {
        return "boful+pid=" + pid + ";sid=" + sid + ";did=1;fil=;pfg=0;truran";
    }
    static String commReqInfo(long pid, long sid) {
        StringBuffer req = Structure.commonReqHeader(1,1);
        req.append("FPT:1\r\n");
        req.append("CID:4294967295\r\n");
        req.append("USN:0\r\n");
        req.append("STL:2\r\n");
        req.append("DFG:0\r\n");
        req.append("DPT:0\r\n");
        req.append("AFG:basic\r\n");
        req.append("URN:anonymity\r\n");
        req.append("PWD:vod\r\n");
        req.append("OS:1\r\n");
        req.append("ULG:2052\r\n");
        req.append("HST:VODsocket\r\n");
        req.append("UIP:192.168.1.1\r\n");
        req.append("MAC:00-00-00-00-00-00\r\n");
        req.append("GW:0.0.0.0\r\n");
        req.append("PFG:0\r\n");
        req.append("FIL:\r\n");
        req.append("ST1:\r\n");
        req.append("ST2:\r\n");
        req.append("PID:" + pid + "\r\n");
        req.append("SID:" + sid + "\r\n");
        req.append("DID:1\r\n");
        req.append("MGC:" + Structure.mgc_hash(pid, sid) + "\r\n");
        req.append("SPL:0\r\n");
        req.append("SPH:0\r\n");
        req.append("\r\n");
        return req.toString();
    }
    static String commReqSeekBasic(long pos) {
        StringBuffer req = Structure.commonReqHeader(1,3);
        req.append("SPL:" + (pos & 0xffffffff) + "\r\n");
        req.append("SPH:" + (pos >> 32) + "\r\n");
        req.append("\r\n");
        return req.toString();
    }
    static String commReqSeek() {
        return Structure.commReqSeekBasic(0);
    }
    static String commReqSeek(long pos) {
        return Structure.commReqSeekBasic(pos);
    }
    static String commReqNop() {
        return Structure.commonReqHeader(1,2).append("\r\n").toString();
    }
    static String dataTranferReq(long cid, long usn, String time) {
        StringBuffer sb = new StringBuffer("GET / HTTP/1.0\r\n");
        sb.append("Accept: */*\r\n");
        sb.append("User-Agent: BMTPAgent\r\n");
        sb.append("Host: VOD\r\n");
        sb.append("UGT:NETPLAYER\r\n");
        sb.append("WAY:2\r\n");
        sb.append("FPT:1\r\n");
        sb.append("CID:" + cid + "\r\n");
        sb.append("USN:" + usn + "\r\n");
        sb.append("STT:" + time + "\r\n");
        sb.append("\r\n");
        return sb.toString();
    }

}
