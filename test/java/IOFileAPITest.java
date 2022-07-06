import org.employee_payroll.FileUtility;
import org.employee_payroll.WatchServices;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.WatchService;
import java.util.stream.IntStream;

public class IOFileAPITest {
    private static final String HOME=System.getProperty("user.home");
    private static final String PLAY_WITH_IO="PlayGround";
    @Test
    void givenPathName_WhenChecked_ThenConfirm() throws IOException {
        Path homePath= Paths.get(HOME);
        Assertions.assertTrue(Files.exists(homePath));

        Path playPath = Paths.get(HOME+"/"+PLAY_WITH_IO);
        if(Files.exists(playPath))
            FileUtility.deleteFiles(playPath.toFile());
        Assertions.assertTrue(Files.notExists(playPath));

        Files.createDirectory(playPath);
        Assertions.assertTrue(Files.exists(playPath));

        IntStream.range(1,10).forEach(cntr->{
            Path tempFile = Paths.get(playPath+"/temp"+cntr);
            Assertions.assertTrue(Files.notExists(tempFile));
            try {
                Files.createFile(tempFile);
            }catch (IOException e){
                Assertions.assertTrue(Files.exists(tempFile));
            }
        });

        Files.list(playPath).filter(Files::isRegularFile).forEach(System.out::println);
        Files.newDirectoryStream(playPath).forEach(System.out::println);
        Files.newDirectoryStream(playPath,path->path.toFile().isFile()&&
                path.toString().startsWith("temp")).forEach(System.out::println);
    }

    @Test
    void givenDirestory_WhenWatched_ListsAllTheActivities() throws IOException {
        Path dir =Paths.get(HOME+"/"+PLAY_WITH_IO);
        Files.list(dir).filter(Files::isRegularFile).forEach(System.out::println);
        new WatchServices(dir).processEvents();
    }
}
