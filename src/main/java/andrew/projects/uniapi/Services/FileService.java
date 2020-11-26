package andrew.projects.uniapi.Services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import pepe.projects.unitest.Utils.StringUtils;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Service
public class FileService {
    @Value("${upload.path}")
    private String uploadPath;

    public String putFile(MultipartFile file) throws IOException {
        String uniqueName = UUID.randomUUID().toString() + file.getOriginalFilename();
        file.transferTo(new File(uploadPath + StringUtils.xml10Normalize(uniqueName)));
        return uniqueName;
    }
    public Boolean deleteFile(String name){
        return  new File(uploadPath+name).delete();
    }
}
