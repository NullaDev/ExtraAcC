package cn.nulladev.extraacc.core;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.nio.file.Files;

import javax.swing.JOptionPane;

import cn.lambdalib.util.generic.RegistryUtils;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;

public class VanillaAcCConfigHacker {
	
	static final String FUCK = "# Fuck Vanilla AcC!";
	
	public static void try_to_register_firstly() {
		
		ResourceLocation defaultRes = new ResourceLocation("extraacc:config/default.conf");

        File customFile = new File("config/academy-craft-data.conf");
        try {
        	if (customFile.isFile() && customFile.exists()) {
        		InputStreamReader read = new InputStreamReader(new FileInputStream(customFile));
    			BufferedReader bufferedReader = new BufferedReader(read);
    			String s = bufferedReader.readLine();
    			if (s.equals(FUCK)) {
        			bufferedReader.close();	
    				return;
    			} else {
    		        if (!customFile.delete()) {
    		        	String str = "Waring: your game is about to crash.\n"
    		        			+ "That's because I can't change the config file of AcademyCraft.\n"
    		        			+ "You can delete the config files (or even the whole config folder) and try again.\n"
    		        			+ "If that doesn not work, please contact me at chitose@nulladev.cn ,and I'll fix it.";
    		        	JOptionPane.showMessageDialog(null, str, "error!", JOptionPane.ERROR_MESSAGE);
    		        }
    			}
    			bufferedReader.close();	
            }
            Files.copy(RegistryUtils.getResourceStream(defaultRes), customFile.toPath());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

}
