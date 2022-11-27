package fr.su.smartnewsvisu.integrationtest;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.apache.commons.io.FileUtils;
import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.springframework.beans.factory.annotation.Autowired;

import fr.su.back.common.exception.BusinessException;
import fr.su.smartnewsvisu.services.StorageBucketService;

/**
 * Test le service de lecture dans les buckets
 */
public class StorageBucketServiceIT extends AbstractStorageIT {

    public static final String ROOT_PATH_RESOURCES = "src/test/resources/bucket/";
    @Autowired
    StorageBucketService storageBucketService;

    @Test
    @DisplayName("Test la récupération d'un fichier dans le bucket")
    public void read_ok() throws IOException, BusinessException {
        // GIVEN
        final String fileName = "Logo---Super-U.jpg";
        createBucketObject(fileName, ROOT_PATH_RESOURCES + fileName);

        // WHEN
        byte[] read = storageBucketService.read(fileName);

        // THEN
        final byte[] image = Files.readAllBytes(Path.of(ROOT_PATH_RESOURCES + fileName));
        Assert.assertArrayEquals(image, read);
    }
}
