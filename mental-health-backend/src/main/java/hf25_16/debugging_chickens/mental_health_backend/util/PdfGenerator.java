package hf25_16.debugging_chickens.mental_health_backend.util;

import com.itextpdf.html2pdf.HtmlConverter;
import hf25_16.debugging_chickens.mental_health_backend.dto.user.response.UserDataResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import java.io.ByteArrayOutputStream;

@Component
public class PdfGenerator {

    private final TemplateEngine templateEngine;

    @Autowired
    public PdfGenerator(TemplateEngine templateEngine) {
        this.templateEngine = templateEngine;
    }

    public byte[] generateUserDataPdf(UserDataResponseDTO userData) {
        Context context = new Context();
        context.setVariable("userData", userData);

        String htmlContent = templateEngine.process("user_data", context);

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        HtmlConverter.convertToPdf(htmlContent, byteArrayOutputStream);

        return byteArrayOutputStream.toByteArray();
    }
}