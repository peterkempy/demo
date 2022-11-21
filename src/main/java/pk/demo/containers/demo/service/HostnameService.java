package pk.demo.containers.demo.service;

import com.sun.jna.Library;
import com.sun.jna.Memory;
import com.sun.jna.Native;
import com.sun.jna.Platform;
import com.sun.jna.Pointer;
import com.sun.jna.platform.win32.Kernel32Util;
import org.json.simple.JSONObject;

public class HostnameService {

    private interface UnixCLibrary extends Library {
        UnixCLibrary INSTANCE = (UnixCLibrary) Native.loadLibrary("c", UnixCLibrary.class);
        public int gethostname(byte[] hostname, int bufferSize);
    }

    public static String getMachineDetails() {
        JSONObject machineDetails = new JSONObject();
        machineDetails.put("hostName", getHostname());
        machineDetails.put("ipAddress", getIpAddress());
        return machineDetails.toJSONString();
    }

    public static String getIpAddress() {
        try {
            java.util.Enumeration<java.net.NetworkInterface> interfaces = java.net.NetworkInterface.getNetworkInterfaces();
            while (interfaces.hasMoreElements()) {
                java.net.NetworkInterface iface = interfaces.nextElement();
                if (iface.isLoopback() || !iface.isUp() || iface.isVirtual() || iface.isPointToPoint())
                    continue;

                java.util.Enumeration<java.net.InetAddress> addresses = iface.getInetAddresses();
                while(addresses.hasMoreElements()) {
                    java.net.InetAddress addr = addresses.nextElement();

                    final String ip = addr.getHostAddress();
                    if(java.net.Inet4Address.class == addr.getClass()) return ip;
                }
            }
        } catch (java.net.SocketException e) {
            throw new RuntimeException(e);
        }
        return null;

    }
    public static String getHostname() {
        if (Platform.isWindows()) {
            return Kernel32Util.getComputerName();
        } else {
            // For now, we'll consider anyhting other than Windows to be unix-ish enough to have gethostname
            // TODO - Consider http://stackoverflow.com/a/10543006 as a possibly better MacOS option

            byte[] hostnameBuffer = new byte[4097];
            // http://pubs.opengroup.org/onlinepubs/9699919799/basedefs/limits.h.html suggests
            // the actual limit would be 255.

            int result = UnixCLibrary.INSTANCE.gethostname(hostnameBuffer, hostnameBuffer.length);
            if (result != 0) {
                throw new RuntimeException("gethostname call failed");
            }

            return Native.toString(hostnameBuffer);
        }
    }
}