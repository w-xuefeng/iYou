package cn.com.wangxuefeng.iyou.util;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import cn.com.wangxuefeng.iyou.bean.Media;

public class Bmtp {
    static final int TIMEOUT = 0;
    InetSocketAddress socketOption = new InetSocketAddress(VODConfig.IPAddress, VODConfig.Port);
    Media media = new Media();
    Socket dSocket = new Socket(); // 数据流
    Socket cSocket = new Socket(); // 控制流

    public Bmtp(long pid) {
        this.media.setPid(pid);
    }
    public Bmtp(long pid, InetSocketAddress socketOption) {
        this.media.setPid(pid);
        this.socketOption = socketOption;
    }
    public Bmtp(long pid, long sid) {
        this.media.setPid(pid);
        this.media.setSid(sid);
    }
    public Bmtp(long pid, long sid, InetSocketAddress socketOption) {
        this.media.setPid(pid);
        this.media.setSid(sid);
        this.socketOption = socketOption;
    }

    EventEmitter download(String savePath) throws IOException {
        this.media = this.getMediaInfo();
        String req = Structure.dataTranferReq(this.media.getCid(), this.media.getUsn(), this.media.getTime());
        savePath += (this.media.getVideoName() + this.media.getFileExt());
        System.out.println(savePath);

        EventEmitter event = new EventEmitter();
        this.dSocket.connect(this.socketOption);
        PrintWriter pWriter = new PrintWriter(this.dSocket.getOutputStream(),true);
        pWriter.println(req);

//        DataOutputStream out = new DataOutputStream(this.dSocket.getOutputStream());
//        out.writeUTF(req);
//        out.flush();

        DataInputStream in = new DataInputStream(this.dSocket.getInputStream());
        int temp = 0;
        List<IntBuffer> data = new ArrayList<>();
        while((temp = in.read()) !=-1 ) {
            System.out.println(temp);
            data.add(new IntBuffer(temp));
        }
        System.out.println(data.size());






//        DataOutputStream out = new DataOutputStream(this.dSocket.getOutputStream());
//        byte[] reqByte = req.getBytes();
//        for (int i = 0; i < reqByte.length; i++){
//            out.write(reqByte[i]);
//            out.flush();
//        }

//        DataInputStream in = new DataInputStream(this.dSocket.getInputStream());
//        int temp = 0;
//        List<IntBuffer> data = new ArrayList();
//        while((temp = in.read())!=-1){
//            System.out.println(temp);
//            data.add(new IntBuffer(temp));
//        }

//        File file = new File(savePath);
//        if(!file.exists()){
//            file.createNewFile();
//        }
//        FileWriter fileWriter = new FileWriter(file, true);
//        for(int s = 0; s < data.size(); s++){
//            System.out.println(data.get(s).value);
//            fileWriter.write(data.get(s).value);
//            fileWriter.flush();
//        }



        return event;
    }

    void saveToFile(String savePath) throws IOException {
        if(savePath == null || savePath.trim().equals("")) {
            String warn = "filepath is required!";
            return;
        }
        EventEmitter downEvent = this.download(savePath);
        downEvent.on("data", new IEventHandler() {
            @Override
            public void handleEvent(List<Object> params) {
                System.out.println("文件接收了" + params.toString());
            }
        });
        downEvent.on("end", new IEventHandler() {
            @Override
            public void handleEvent(List<Object> params) {
                System.out.println("接收完成，文件存为" + params.toString());
            }
        });
    }

    Media getMediaInfo() throws IOException {
        this.cSocket.setSoTimeout(TIMEOUT);
        this.cSocket.connect(this.socketOption);
        OutputStreamWriter streamWriter = new OutputStreamWriter(this.cSocket.getOutputStream());
        BufferedWriter bufferedWriter = new BufferedWriter(streamWriter);
        bufferedWriter.write(Structure.commReqInfo(this.media.getPid(), this.media.getSid()));
        bufferedWriter.flush();

        BufferedInputStream streamReader = new BufferedInputStream(this.cSocket.getInputStream());
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(streamReader, "GB2312"));

        char[] info = new char[300];
        bufferedReader.read(info,0,300);
        String dataInfo = String.valueOf(info);
        bufferedReader.close();
        bufferedWriter.close();
        this.cSocket.close();
        MediaInfoParser data = new MediaInfoParser(dataInfo.getBytes("GB2312"));
        Media mediaInfo = data.getMedia();
        mediaInfo.setPid(this.media.getPid());
        mediaInfo.setSid(this.media.getSid());
        return mediaInfo;
    }

    class IntBuffer {
        int value;
        public IntBuffer(int value) {
            this.value = value;
        }
    }
}
