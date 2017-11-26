import javax.annotation.security.RunAs
import javax.servlet.http.*
import org.springframework.web.multipart.*
import java.nio.file.*
import java.nio.charset.*

@Controller
class CertSigner {

    public static final String DATADIR = "/tmp/data/";
    public static final String IRON_HOME = System.getProperty("user.home") + "/.iron";

    @RequestMapping("/certsign/{name}")
    @ResponseBody
    String home(@PathVariable String name) {
        return "Hello "+name;
    }

    @RequestMapping(value = "/uploadFile", method = RequestMethod.POST)
    @ResponseBody
    public String submit(@RequestParam("file") MultipartFile file, ModelMap modelMap) {
        modelMap.addAttribute("file", file);
        convert(file);
        return "fileUploadView";
    }

    @RequestMapping(value = "/upload/{servicename}/{clientname}", method = RequestMethod.POST)
    @ResponseBody
    public String submitWithParam(
        @RequestParam("file") MultipartFile file,
        @PathVariable("servicename") String servicename,
        @PathVariable("clientname") String clientname,
        HttpServletRequest request,
        ModelMap modelMap)
    {
        modelMap.addAttribute("file", file);
        convert(file, clientname+".csr");
        String ip = request.getRemoteAddr();
	//ip --> client mapping possible here

        String cmd =  "iron certificate sign "+servicename+" "+DATADIR+clientname+".csr";
        cmd.execute();
        String certPath = IRON_HOME + "/"+ servicename + "/certs/" + clientname + "/" + clientname + ".crt";

        File certFile = new File(certPath);
        byte[] encoded = Files.readAllBytes(Paths.get(certPath));
        String certContent = new String(encoded, StandardCharsets.UTF_8);
        return certContent;
    }

    public static File convert(MultipartFile file)
    {
        String savedName = file.getOriginalFilename();
        return convert(file, savedName);
    }

    public static File convert(MultipartFile file, String savedName)
    {
        File convFile = new File(DATADIR + savedName);
        convFile.createNewFile();
        FileOutputStream fos = new FileOutputStream(convFile);
        fos.write(file.getBytes());
        fos.close();
        return convFile;
    }
}
