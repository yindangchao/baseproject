package heyhou.com.baseproject.utils;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.text.TextUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 创建:yb 2016/11/21.
 * 描述:
 */

public class ContactUtil {
    private Context mContext;
    private static final String[] PHONES_PROJECTION = new String[] {
            Phone.DISPLAY_NAME, Phone.NUMBER, Phone.PHOTO_ID, Phone.CONTACT_ID };
    private static final int PHONES_NUMBER_INDEX = 1;
    private static ContactUtil instance;
    private static ExecutorService service;

    private ContactUtil(Context context) {
        this.mContext = context;
        if (service == null)
            service = Executors.newSingleThreadScheduledExecutor();
    }

    public static ContactUtil build(Context context) {
        if (instance == null) {
            synchronized (ContactUtil.class) {
                if (instance == null) {
                    instance = new ContactUtil(context);
                }
            }
        }
        return instance;
    }

    /**
     * 获取通讯录
     * @param callBack
     */
    public void queryContacts(final ContactCallBack callBack) {
        service.submit(new Runnable() {
            @Override
            public void run() {
                if (callBack != null)
                    callBack.onSuccess(getContacts());
            }
        });
    }

    public List<ContactInfo> getContacts() {
        List<ContactInfo> result = new ArrayList<>(0);
        ContentResolver resolver = mContext.getContentResolver();
        Cursor phoneCursor = resolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                PHONES_PROJECTION, null, null, null);
        if (phoneCursor != null) {
            while (phoneCursor.moveToNext()) {
                // get phonenumber
                String phoneNumber = phoneCursor.getString(PHONES_NUMBER_INDEX);
                // IF IS NULL CONTINUE
                if (TextUtils.isEmpty(phoneNumber)) {
                    continue;

                }
                // get name
                String contactName = phoneCursor.getString(0);

                Long contactid=phoneCursor.getLong(3);
                Long photoid = phoneCursor.getLong(2);
                ContactInfo contactInfo = new ContactInfo();
                // get the photo
                if (photoid > 0) {
                    Uri uri = ContentUris.withAppendedId(
                            ContactsContract.Contacts.CONTENT_URI,
                            contactid);
                    contactInfo.setC_photo(uri);
                }

                contactInfo.setC_name(contactName);
                contactInfo.setC_phone(phoneNumber);
                result.add(contactInfo);

            }
        }
        phoneCursor.close();
        return result;
    }

    public static interface ContactCallBack {
        void onSuccess(List<ContactInfo> contacts);
    }

    public static class ContactInfo {
        private Uri c_photo = null;
        private String c_name = null;
        private String c_phone = null;
        private String c_email=null;
        private String c_birthday=null;
        private String c_qq=null;
        private String c_address=null;

        public Uri getC_photo() {
            return c_photo;
        }

        public void setC_photo(Uri c_photo) {
            this.c_photo = c_photo;
        }

        public String getC_email() {
            return c_email;
        }

        public void setC_email(String c_email) {
            this.c_email = c_email;
        }

        public String getC_birthday() {
            return c_birthday;
        }

        public void setC_birthday(String c_birthday) {
            this.c_birthday = c_birthday;
        }

        public String getC_qq() {
            return c_qq;
        }

        public void setC_qq(String c_qq) {
            this.c_qq = c_qq;
        }

        public String getC_address() {
            return c_address;
        }

        public void setC_address(String c_address) {
            this.c_address = c_address;
        }

        public String getC_name() {
            return c_name;
        }

        public void setC_name(String c_name) {
            this.c_name = c_name;
        }

        public String getC_phone() {
            return c_phone;
        }

        public void setC_phone(String c_phone) {
            this.c_phone = c_phone;
        }
    }

}
