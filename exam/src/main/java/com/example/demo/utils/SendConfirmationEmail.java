package com.example.demo.utils;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

public class SendConfirmationEmail {

    public static void sendConfirmationEmailStatic(String email, String taikhoan, JavaMailSender mailSender) {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("Chào mừng bạn đến với HieuNM151");
        try {
            helper.setTo(email);
            helper.setSubject("Chào mừng bạn đến với HieuNM151");

            String htmlMsg = "<h1>Chào mừng bạn đến với <span style='color: #ff9900;'>HieuNM151</span></h1>\n" +
                    "<p>Xin chân thành cảm ơn bạn đã tham gia <span style='color: #ff9900;'>HieuNM151</span> của chúng tôi. Chúng tôi sẽ cập nhật cho bạn thông tin\n" +
                    "    mới nhất về tin tức và trạng thái.</p>\n" +
                    "<h3>Ưu điểm của <span style='color: #ff9900;'>HieuNM151</span>:</h3>\n" +
                    "<ul>\n" +
                    "    <li>Thông tin mới nhất về sản phẩm, dịch vụ và dự án của chúng tôi</li>\n" +
                    "    <li>Đã ngộ đặc biệt và phần thưởng hấp dẫn</li>\n" +
                    "</ul>\n" +
                    "<p><strong>Cảnh báo:</strong> Vui lòng không cung cấp thông tin tài khoản cho người khác để tránh lộ thông tin!\n" +
                    "    :</p>\n" +
                    "<a href='LINK_DEN_TRANG_DANG_KY'\n" +
                    "    style='padding: 10px 20px; background-color: #ff9900; color: #ffffff; text-decoration: none; border-radius: 5px;'>Trang web</a>" +
                    "<p><strong>Thông tin đăng nhập:</strong></p>" +
                    "<p>Tài khoản: <strong>" + taikhoan + "</strong></p>" +
                    "<p>Mật khẩu: <strong>123456</strong></p>"; // Thêm mật khẩu vào nội dung email

            helper.setText(htmlMsg, true);
            mailSender.send(mimeMessage);
        } catch (MessagingException e) {
            // Xử lý ngoại lệ nếu gửi email thất bại
            e.printStackTrace();
        }
    }

}
