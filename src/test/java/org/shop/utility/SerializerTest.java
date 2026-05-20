package org.shop.utility;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.shop.data.Cashier;
import org.shop.data.Receipt;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SerializerTest {

    @TempDir
    Path tempDir;

    private Receipt createReceipt() {
        Cashier cashier = new Cashier("Alice", new BigDecimal("3000.00"));
        return new Receipt(cashier, List.of(), new BigDecimal("99.99"));
    }

    // --- serialize ---
    @Test
    void serialize_shouldCreateFile() throws IOException {
        String path = tempDir.resolve("receipt.ser").toString();

        Serializer.serialize(path, createReceipt());

        assertTrue(tempDir.resolve("receipt.ser").toFile().exists());
    }

    @Test
    void serialize_shouldThrowIOException_whenPathIsInvalid() {
        String invalidPath = tempDir.resolve("nonexistent/dir/receipt.ser").toString();
        assertThrows(IOException.class, () -> Serializer.serialize(invalidPath, createReceipt()));
    }

    // --- deserialize ---

    @Test
    void deserialize_shouldReturnObjectWithSameData_whenFileExists() throws IOException, ClassNotFoundException {
        Receipt receipt = createReceipt();
        String path = tempDir.resolve("receipt.ser").toString();

        Serializer.serialize(path, receipt);
        Receipt result = Serializer.deserialize(path, Receipt.class);

        assertEquals(receipt.getReceiptNo(), result.getReceiptNo());
        assertEquals(receipt.getTotalSum(), result.getTotalSum());
    }

    @Test
    void deserialize_shouldThrowIOException_whenFileNotFound() {
        String path = tempDir.resolve("missing.ser").toString();
        assertThrows(IOException.class, () -> Serializer.deserialize(path, Receipt.class));
    }
}
