package uz.pdp.clickup.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import uz.pdp.clickup.entity.Attachment;
import uz.pdp.clickup.repository.AttachmentRepository;
import uz.pdp.clickup.service.AttachmentService;

import javax.servlet.http.HttpServletResponse;
import java.nio.file.*;

@Service
public class AttachmentServiceImpl implements AttachmentService {

    @Autowired
    AttachmentRepository attachmentRepository;

    private final Path root = Paths.get("C:\\");

    @Override
    public void download(String fileName, HttpServletResponse response) {
        Attachment attachment = attachmentRepository.findByName(fileName);

        response.setHeader("Content-Disposition", "attachment; filename=\"" + attachment.getOriginalName() + "\"");
        response.setContentType(attachment.getContentType());

        Path filePath = root.resolve(fileName).normalize();

        try {
            FileCopyUtils.copy(Files.newInputStream(Paths.get(filePath.toString())), response.getOutputStream());
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
    }
}
