package org.shop.utility;

import java.io.*;

public class Serializer {
    public static <T extends Serializable> void serialize(String filePath, T object) throws IOException {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(filePath))) {
            out.writeObject(object);
        }
    }

    public static <T> T deserialize(String filePath, Class<T> clazz) throws IOException, ClassNotFoundException {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(filePath))) {
            return clazz.cast(in.readObject());
        }
    }
}
