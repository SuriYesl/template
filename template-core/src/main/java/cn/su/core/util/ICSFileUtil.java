package cn.su.core.util;

import net.fortuna.ical4j.data.CalendarOutputter;
import net.fortuna.ical4j.model.*;
import net.fortuna.ical4j.model.component.VAlarm;
import net.fortuna.ical4j.model.component.VEvent;
import net.fortuna.ical4j.model.component.VTimeZone;
import net.fortuna.ical4j.model.property.*;
import net.fortuna.ical4j.util.UidGenerator;

import java.io.FileOutputStream;

/**
 * @Author: su rui
 * @Date: 2021/1/7 17:01
 * @Description: ics文件工具类
 */
public class ICSFileUtil {
    public static void createICSFile(long startTime, long endTime, int minute,
                                     String address, String timeZone,
                                     String note, String fileLocation) {
        try {
            TimeZoneRegistry registry = TimeZoneRegistryFactory.getInstance().createRegistry();
            TimeZone timezone = registry.getTimeZone(timeZone);
            VTimeZone tz = timezone.getVTimeZone();

            Calendar calendar = new Calendar();
            calendar.getProperties().add(new ProdId("-//Ben Fortuna//iCal4j 1.0//EN"));
            calendar.getProperties().add(Version.VERSION_2_0);
            calendar.getProperties().add(CalScale.GREGORIAN);

            VEvent event = new VEvent(new Date(startTime), new Date(endTime), "Appointment");
            event.getProperties().add(new Location(address));
            // 生成唯一标示
            event.getProperties().add(new Uid(new UidGenerator("iCal4j").generateUid().getValue()));
            // 添加时区信息
            event.getProperties().add(tz.getTimeZoneId());

            Description description = new Description();
            description.setValue(note);
            event.getProperties().add(description);

            VAlarm valarm = new VAlarm(new Dur(0, 0, minute, 0));
//            VAlarm valarm = new VAlarm(new Dur(new Date(startTime), new Date(endTime)));
            valarm.getProperties().add(new Summary("Event Alarm"));
            valarm.getProperties().add(Action.DISPLAY);
            valarm.getProperties().add(new Description("Appointment"));
            event.getAlarms().add(valarm);

            calendar.getComponents().add(event);
            calendar.validate();
            FileOutputStream fileOut = new FileOutputStream(fileLocation + ".ics");
            CalendarOutputter outPutter = new CalendarOutputter();
            outPutter.output(calendar, fileOut);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
