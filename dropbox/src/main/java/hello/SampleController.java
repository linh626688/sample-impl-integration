package hello;

import com.dropbox.core.DbxException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.*;
import org.springframework.boot.autoconfigure.*;
import org.springframework.stereotype.*;
import org.springframework.web.bind.annotation.*;

@Controller
@EnableAutoConfiguration
public class SampleController {
    DropboxService dropboxService;

    @RequestMapping("/hello")
    @ResponseBody
    String home() {
        try {
            dropboxService = new DropboxService();
            dropboxService.DropboxUtil();
        } catch (DbxException e) {
            e.printStackTrace();
        }
        return "Hello World!";
    }

    public static void main(String[] args) throws Exception {
        SpringApplication.run(SampleController.class, args);
    }
}