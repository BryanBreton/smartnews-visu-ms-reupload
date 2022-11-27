package fr.su.smartnewsvisu.services;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.Storage;

import fr.su.back.common.exception.BusinessException;
import fr.su.back.common.objects.error.Error;
import fr.su.smartnewsvisu.exceptions.SmartnewsVisuExceptionCodes;

/**
 * Permet de lire le contenu d'un bucket
 */
@Component
public class StorageBucketService {

    private static final Logger LOGGER = LogManager.getLogger(StorageBucketService.class);

    private final Storage storage;

    private final String bucketName;

    @Autowired
    public StorageBucketService(final Storage storage, @Value("${gcp.input.bucket.name}") String bucketName) {
        this.storage = storage;
        this.bucketName = bucketName;
    }

    /**
     * Récupère un fichier dans le bucket
     *
     * @param objectName identifiant du fichier dans le bucket
     * @return
     */
    public byte[] read(final String objectName) throws BusinessException {
        var blob = storage.get(BlobId.of(bucketName, objectName));
        if (blob != null) {
            return blob.getContent();
        } else {
            // TODO on fait quoi ?
            LOGGER.error("Object {} non trouvé dans le bucket {}", objectName, bucketName);
            final Error error = new Error(null, SmartnewsVisuExceptionCodes.STORAGE_OBJET_NON_TROUVE.getCode(), null, null, null, null);
            throw new BusinessException(error);
        }
    }

}
