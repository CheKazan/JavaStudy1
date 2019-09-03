import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

public class ZipSolution {

        public static void main(String[] args) throws IOException {
            //String file = args[0];
            // String zipfile = args[1];
            String file = "d:\\result.txt";
            String zipfile = "d:\\archive.zip";
            // read data from file
            FileInputStream fis = new FileInputStream(file);
            byte[] filecontent = new byte[fis.available()];
            fis.read(filecontent);
            fis.close();

            // create archive if it's doesn't exist
            if(!Files.exists(Paths.get(zipfile))) Files.createFile(Paths.get(zipfile));

            //save all zipentry from archive
            FileInputStream fis2 = new FileInputStream(zipfile);
            ZipInputStream zis = new ZipInputStream(fis2);
            // HashMap<ZipEntry,byte[]> zipContent = new HashMap<>();
            HashMap<String,byte[]> zipContent = new HashMap<>();
            ZipEntry entry;
            while((entry = zis.getNextEntry())!=null) {
                byte [] content = new byte[(int) entry.getSize()];
                zis.read(content);
                //zipContent.put(entry,content);
                zipContent.put(entry.getName(),content);
            }
            zis.close();

            //add new ZisEntry for my file
            ZipEntry newZipEntry = new ZipEntry("\\new\\"+ Paths.get(file).getFileName());
            //zipContent.put(newZipEntry,filecontent);
            zipContent.put("\\new\\"+ Paths.get(file).getFileName(),filecontent);

            // write all map to archive
            ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(zipfile));
            for(Map.Entry<String,byte[]> m: zipContent.entrySet()) {
                zos.putNextEntry(new ZipEntry(m.getKey()));
                zos.write(m.getValue());
                zos.closeEntry();
            }
            zos.close();


        }
    }

