package fr.su.smartnewsvisu.integrationtest;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;

import org.apache.commons.lang3.Validate;
import org.junit.jupiter.api.AfterEach;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import com.google.cloud.storage.Blob;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;

import fr.su.smartnewsvisu.SmartnewsVisuApplication;

/**
 * classe abstraite permettant de lancer des test d'intégration avec GCP
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {
        SmartnewsVisuApplication.class }, properties = "spring.main.allow-bean-definition-overriding=true", webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
public abstract class AbstractStorageIT {

    @Autowired
    Storage storage;

    @Value("${gcp.input.bucket.name}")
    String inputBucketName;

    /**
     * Consomme les messages restant dans la souscription entre chaque test.
     */
    @AfterEach
    void teardown() {
        // on vide le bucket
        storage.list(inputBucketName).iterateAll().forEach(blob -> storage.delete(blob.getBlobId()));
        // waitAtMost(Duration.ofSeconds(10)).until(() -> !storage.list(inputBucketName).iterateAll().iterator().hasNext());
    }

    /**
     * permet de créer un objet dans un bucket à partir d'un fichier
     *
     * @param objectName nom de l'objet dans le bucket
     * @param fileName   nom du fichier source
     * @return id de l'objet dans le bucket
     */
    void createBucketObject(String objectName, String fileName) throws IOException {
        final BlobInfo blobInfo = BlobInfo.newBuilder(BlobId.of(inputBucketName, objectName)).build();
        final byte[] fileContent = Files.readAllBytes(Path.of(fileName));
        Validate.notNull(fileContent);
        storage.create(blobInfo, fileContent);
    }

}
