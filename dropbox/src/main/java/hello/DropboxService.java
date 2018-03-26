package hello;

import com.dropbox.core.DbxException;
import com.dropbox.core.DbxRequestConfig;
import com.dropbox.core.v2.DbxClientV2;
import com.dropbox.core.v2.files.FileMetadata;
import com.dropbox.core.v2.files.ListFolderResult;
import com.dropbox.core.v2.files.Metadata;
import com.dropbox.core.v2.users.FullAccount;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.util.List;

import java.io.FileInputStream;
import java.io.InputStream;
import java.io.IOException;

/**
 * Created by LINHDT on 03/26/2018.
 */
@Service
@Component
public class DropboxService {
    private static final String ACCESS_TOKEN = "vqmV8LdCN0AAAAAAAAAAQk2W3o5Xlf3-r2zgY6Nd25IcIJ8K5ay-E3ggZ_Ae77Qr";

    DbxRequestConfig config = new DbxRequestConfig("dropbox/java-tutorial", "en_US");
    DbxClientV2 client = new DbxClientV2(config, ACCESS_TOKEN);

    public void DropboxUtil() throws DbxException {
        FullAccount account = client.users().getCurrentAccount();
        System.out.println(account.getName().getDisplayName());

        // Get files and folder metadata from Dropbox root directory
        ListFolderResult result = client.files().listFolder("");
        while (true) {
            for (Metadata metadata : result.getEntries()) {
                System.out.println(metadata.getPathLower());
            }

            if (!result.getHasMore()) {
                break;
            }

            result = client.files().listFolderContinue(result.getCursor());
        }

        // Upload "test.txt" to Dropbox
        try (InputStream in = new FileInputStream("test.txt")) {
            FileMetadata metadata = client.files().uploadBuilder("/test.txt")
                    .uploadAndFinish(in);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
