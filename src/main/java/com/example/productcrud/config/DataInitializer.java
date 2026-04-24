package com.example.productcrud.config;

import com.example.productcrud.model.Category;
import com.example.productcrud.model.Product;
import com.example.productcrud.model.User;
import com.example.productcrud.repository.CategoryRepository;
import com.example.productcrud.repository.ProductRepository;
import com.example.productcrud.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class DataInitializer {

/**
 * Database initializer - hanya jalan jika tidak ada user sama sekali
 * Ini menjaga agar data user, kategori, dan produk tetap aman saat restart
 */
    @Bean
    public CommandLineRunner initData(UserRepository userRepository, 
                                       CategoryRepository categoryRepository,
                                       ProductRepository productRepository,
                                       PasswordEncoder passwordEncoder) {
        return args -> {
            // AMAN: Hanya inisialisasi jika database 100% kosong
            // Ini mencegah penghapusan/duplikasi 100 produk yang sudah ada
            if (userRepository.count() == 0 && categoryRepository.count() == 0) {
                User admin = new User();
                admin.setUsername("admin");
                admin.setEmail("admin@example.com");
                admin.setFullName("Admin User");
                admin.setPassword(passwordEncoder.encode("admin123"));
                admin.setEnabled(true);
                userRepository.save(admin);
                
                String[] defaultCategories = {"Elektronik", "Buku", "Makanan", "Pakaian"};
                Map<String, Category> categoryMap = new HashMap<>();
                Arrays.stream(defaultCategories).forEach(name -> {
                    Category cat = new Category();
                    cat.setName(name);
                    cat.setDescription("Default category: " + name);
                    cat.setUser(admin);
                    categoryRepository.save(cat);
                    categoryMap.put(name, cat);
                });
                
                System.out.println("Default admin & categories created.");
                
                // Panggil method inisialisasi 100 produk
                initProducts(productRepository, categoryMap, admin);
            } else {
                System.out.println("Database sudah terisi. Melewati inisialisasi data awal.");
            }
        };
    }

    private void initProducts(ProductRepository productRepository, Map<String, Category> categoryMap, User admin) {
        Product[] elektronikProducts = {
            new Product(null, "Smartphone Samsung Galaxy S24 Ultra", categoryMap.get("Elektronik"), 18500000, 25, "Ponsel flagship dengan kamera 200MP dan layar AMOLED 6.8 inci", true, LocalDate.now(), "admin", "admin"),
            new Product(null, "Laptop ASUS ROG Strix G16", categoryMap.get("Elektronik"), 23500000, 12, "Laptop gaming dengan prosesor Intel Core i9 dan RTX 4070", true, LocalDate.now(), "admin", "admin"),
            new Product(null, "Headphone Sony WH-1000XM5", categoryMap.get("Elektronik"), 4500000, 40, "Headphone wireless premium dengan noise cancelling terbaik", true, LocalDate.now(), "admin", "admin"),
            new Product(null, "Smart TV Samsung 55-inch Neo QLED 4K", categoryMap.get("Elektronik"), 12000000, 18, "Televisi 55 inci dengan teknologi Quantum Dot dan HDR10+", true, LocalDate.now(), "admin", "admin"),
            new Product(null, "Apple iPad Pro 12.9-inch M2", categoryMap.get("Elektronik"), 16500000, 22, "Tablet profesional dengan chip M2 dan layar Liquid Retina XDR", true, LocalDate.now(), "admin", "admin"),
            new Product(null, "Kamera Mirrorless Sony Alpha a7 IV", categoryMap.get("Elektronik"), 32000000, 8, "Kamera full-frame 33MP dengan sistem autofokus canggih", true, LocalDate.now(), "admin", "admin"),
            new Product(null, "Smartwatch Apple Watch Series 9", categoryMap.get("Elektronik"), 6500000, 35, "Smartwatch dengan layar OLED dan sensor kesehatan terbarukan", true, LocalDate.now(), "admin", "admin"),
            new Product(null, "Earbuds Samsung Galaxy Buds2 Pro", categoryMap.get("Elektronik"), 2200000, 50, "Earbuds wireless dengan ANC dan kualitas suara AKG", true, LocalDate.now(), "admin", "admin"),
            new Product(null, "Drone DJI Mini 4 Pro", categoryMap.get("Elektronik"), 11000000, 15, "Drone kompak 4K dengan fitur penginderaan rintangan 360 derajat", true, LocalDate.now(), "admin", "admin"),
            new Product(null, "Speaker JBL Charge 5", categoryMap.get("Elektronik"), 1800000, 30, "Speaker portabel waterproof dengan bass kuat dan daya tahan 20 jam", true, LocalDate.now(), "admin", "admin"),
            new Product(null, "Monitor LG UltraGear 27-inch 4K", categoryMap.get("Elektronik"), 8500000, 20, "Monitor gaming 27 inci dengan refresh rate 144Hz dan HDR600", true, LocalDate.now(), "admin", "admin"),
            new Product(null, "Keyboard Logitech MX Mechanical", categoryMap.get("Elektronik"), 2500000, 45, "Keyboard wireless mechanical dengan switch low-profile tactile", true, LocalDate.now(), "admin", "admin"),
            new Product(null, "Mouse Gaming Razer Viper V2 Pro", categoryMap.get("Elektronik"), 1800000, 40, "Mouse wireless gaming dengan sensor 30K DPI dan bobot 58g", true, LocalDate.now(), "admin", "admin"),
            new Product(null, "Power Bank Anker 737 24K mAh", categoryMap.get("Elektronik"), 1200000, 60, "Power bank 24000mAh dengan output 140W USB-C Power Delivery", true, LocalDate.now(), "admin", "admin"),
            new Product(null, "Router WiFi 6 Asus RT-AX86U", categoryMap.get("Elektronik"), 3500000, 25, "Router gaming dual-band dengan kecepatan 5700Mbps dan AiMesh", true, LocalDate.now(), "admin", "admin"),
            new Product(null, "Konsol PlayStation 5 Slim 1TB", categoryMap.get("Elektronik"), 9500000, 12, "Konsol gaming generasi terbaru dengan penyimpanan 1TB dan DualSense", true, LocalDate.now(), "admin", "admin"),
            new Product(null, "Laptop Lenovo ThinkPad X1 Carbon Gen 11", categoryMap.get("Elektronik"), 28500000, 10, "Laptop bisnis ultraportabel dengan layar 14-inch WUXGA dan RAM 32GB", true, LocalDate.now(), "admin", "admin"),
            new Product(null, "Proyektor Epson Home Cinema 5050UB", categoryMap.get("Elektronik"), 18000000, 8, "Proyektor 4K PRO-UHD dengan kecerahan 2600 lumens dan warna HDR", true, LocalDate.now(), "admin", "admin"),
            new Product(null, "Speaker Bluetooth JBL Flip 6", categoryMap.get("Elektronik"), 1500000, 35, "Speaker portabel waterproof IPX7 dengan driver racik 20W", true, LocalDate.now(), "admin", "admin"),
            new Product(null, "Harddisk Eksternal WD My Passport 5TB", categoryMap.get("Elektronik"), 1200000, 45, "Harddisk portabel USB 3.2 dengan kapasitas 5TB dan kecepatan 5Gbps", true, LocalDate.now(), "admin", "admin"),
            new Product(null, "Action Camera GoPro Hero 12 Black", categoryMap.get("Elektronik"), 6500000, 20, "Action camera 5.3K dengan HyperSmooth 6.0 dan GPS bawaan", true, LocalDate.now(), "admin", "admin"),
            new Product(null, "Modem 5G Huawei 5G CPE Pro 2", categoryMap.get("Elektronik"), 2200000, 30, "Modem 5G dengan kecepatan download hingga 3.6Gbps dan Wi-Fi 6", true, LocalDate.now(), "admin", "admin"),
            new Product(null, "Smart Speaker Amazon Echo Dot 5th Gen", categoryMap.get("Elektronik"), 800000, 55, "Smart speaker dengan Alexa dan suara jernih 360 derajat", true, LocalDate.now(), "admin", "admin"),
            new Product(null, "E-Reader Amazon Kindle Paperwhite 11th Gen", categoryMap.get("Elektronik"), 2800000, 28, "E-reader 6.8-inch dengan warm light dan tahan air IPX8", true, LocalDate.now(), "admin", "admin")
        };

        Product[] bukuProducts = {
            new Product(null, "Atomic Habits: Pembiasaan Kecil untuk Hasil Besar", categoryMap.get("Buku"), 89000, 100, "Buku self-help ikonis tentang cara membangun kebiasaan baik dan menghilangkan kebiasaan buruk", true, LocalDate.now(), "admin", "admin"),
            new Product(null, "Sapi yang Dilicik: Suatu Fabel tentang Kepemimpinan", categoryMap.get("Buku"), 65000, 85, "Fabel bisnis klasik tentang manajemen perubahan dan kepemimpinan efektif", true, LocalDate.now(), "admin", "admin"),
            new Product(null, "Mindset: Kekuatan Mindset untuk Sukses", categoryMap.get("Buku"), 95000, 75, "Buku psikologi terkenal tentang pentingnya pola pikir berkembang (growth mindset)", true, LocalDate.now(), "admin", "admin"),
            new Product(null, "The Alchemist: Sang Pendekar", categoryMap.get("Buku"), 75000, 120, "Novel filosofis Paulo Coelho tentang perjalanan spiritual mengejar impian sejati", true, LocalDate.now(), "admin", "admin"),
            new Product(null, "Thinking, Fast and Slow", categoryMap.get("Buku"), 150000, 50, "Karya psikologi Nobel tentang dua sistem berpikir manusia: cepat dan lambat", true, LocalDate.now(), "admin", "admin"),
            new Product(null, "Deep Work: Aturan untuk Fokus Sukses di Dunia yang Terdistraksi", categoryMap.get("Buku"), 110000, 60, "Buku produktivitas tentang cara melakukan pekerjaan bernilai tinggi dengan konsentrasi penuh", true, LocalDate.now(), "admin", "admin"),
            new Product(null, "How to Win Friends and Influence People", categoryMap.get("Buku"), 95000, 70, "Buku self-help klasik Dale Carnegie tentang seni komunikasi dan mempengaruhi orang lain", true, LocalDate.now(), "admin", "admin"),
            new Product(null, "Homo Deus: Sejarah Masa Depan Umat Manusia", categoryMap.get("Buku"), 135000, 45, "Karya Yuval Noah Harari tentang evolusi manusia dan tantangan masa depan di era teknologi", true, LocalDate.now(), "admin", "admin"),
            new Product(null, "The 7 Habits of Highly Effective People", categoryMap.get("Buku"), 125000, 55, "Buku prinsip-prinsip kepemimpinan dan karakter dari Stephen R. Covey", true, LocalDate.now(), "admin", "admin"),
            new Product(null, "Educated: Sebuah Memoir", categoryMap.get("Buku"), 115000, 40, "Memoar Tara Westover tentang perjalanan keluar dari kelompok fanatik melalui pendidikan", true, LocalDate.now(), "admin", "admin"),
            new Product(null, "The Lean Startup: Bagaimana Startup Sukses Saat Ini", categoryMap.get("Buku"), 105000, 65, "Metodologi pengembangan bisnis dan produk melalui eksperimen dan iterasi cepat", true, LocalDate.now(), "admin", "admin"),
            new Product(null, "Sapi yang Dilicik: Suatu Fabel tentang Kepemimpinan", categoryMap.get("Buku"), 65000, 85, "Fabel bisnis klasik tentang manajemen perubahan dan kepemimpinan efektif", true, LocalDate.now(), "admin", "admin"),
            new Product(null, "Man's Search for Meaning", categoryMap.get("Buku"), 95000, 50, "Memoar psikiatr Viktor Frankl tentang menemukan makna hidup di tengah penderitaan kamp konsentrasi", true, LocalDate.now(), "admin", "admin"),
            new Product(null, "The Power of Now: Panduan Praktis Menjadi Sadar", categoryMap.get("Buku"), 85000, 75, "Buku spiritual Eckhart Tolle tentang pentingnya hidup di momen sekarang", true, LocalDate.now(), "admin", "admin"),
            new Product(null, "The Innovator's Dilemma: Ketika Teknologi Baru Menyebabkan Kegagalan", categoryMap.get("Buku"), 120000, 45, "Karya Clayton Christensen tentang inovasi disruptif dan kelangsungan bisnis", true, LocalDate.now(), "admin", "admin"),
            new Product(null, "Sapi yang Dilicik: Suatu Fabel tentang Kepemimpinan", categoryMap.get("Buku"), 65000, 85, "Fabel bisnis klasik tentang manajemen perubahan dan kepemimpinan efektif", true, LocalDate.now(), "admin", "admin"),
            new Product(null, "Principles: Aturan Hidup dan Kerja Ray Dalio", categoryMap.get("Buku"), 140000, 40, "Memoar pendiri Bridgewater Associates tentang prinsip manajemen dan kehidupan berbasis data", true, LocalDate.now(), "admin", "admin"),
            new Product(null, "The Art of War Sun Tzu: Edisi Terbaru", categoryMap.get("Buku"), 65000, 100, "Karya strategi militer kuno tentang taktik dan psikologi kemenangan", true, LocalDate.now(), "admin", "admin"),
            new Product(null, "The 4-Hour Workweek: Lepas dari Zona Nyaman", categoryMap.get("Buku"), 95000, 70, "Buku gaya hidup Timothy Ferriss tentang produktivitas, outsourcing, dan pensiun awal", true, LocalDate.now(), "admin", "admin"),
            new Product(null, "Good to Great: Mengapa Beberapa Perusahaan Melompat dan Beberapa Tidak", categoryMap.get("Buku"), 130000, 50, "Penelitian Jim Collins tentang perusahaan yang bertransformasi dari rata-rata menjadi luar biasa", true, LocalDate.now(), "admin", "admin"),
            new Product(null, "The Richest Man in Babylon: Kekayaan sejak Zaman Kuno", categoryMap.get("Buku"), 75000, 90, "Klasik literasi keuangan George S. Clason melalui kisah-kisah Babilon kuno", true, LocalDate.now(), "admin", "admin"),
            new Product(null, "The One Thing: Fokus pada Satu Hal Penting", categoryMap.get("Buku"), 85000, 65, "Buku produktivitas Gary Keller tentang menentukan prioritas dan mencapai hasil luar biasa", true, LocalDate.now(), "admin", "admin"),
            new Product(null, "Start with Why: Cara Memimpin yang Menginspirasi", categoryMap.get("Buku"), 95000, 55, "Buku Simon Sinek tentang pentingnya tujuan dan kepercayaan dalam kepemimpinan dan bisnis", true, LocalDate.now(), "admin", "admin"),
            new Product(null, "The Compound Effect: Dampak dari Memilih Hidup Lebih Baik", categoryMap.get("Buku"), 80000, 70, "Buku Darren Hardy tentang kekuatan kebiasaan kecil yang menumpuk menjadi hasil besar dari waktu ke waktu", true, LocalDate.now(), "admin", "admin"),
            new Product(null, "Essentialisme: Disiplin Mencari Kurang Lebih", categoryMap.get("Buku"), 95000, 60, "Buku Greg McKeown tentang cara menentukan esensi dan mengeliminasi kebisingan dalam hidup dan kerja", true, LocalDate.now(), "admin", "admin")
        };

        Product[] makananProducts = {
            new Product(null, "Kopi Arabica Toraja 250g", categoryMap.get("Makanan"), 85000, 100, "Kopi arabica premium dari Toraja dengan cita rasa khas dan aroma wangi", true, LocalDate.now(), "admin", "admin"),
            new Product(null, "Cokelat Belgia Premium Dark 70% Cocoa", categoryMap.get("Makanan"), 120000, 80, "Cokelat belgia asli dengan kandungan kakao 70% dan tekstur lembut meleleh", true, LocalDate.now(), "admin", "admin"),
            new Product(null, "Pasta Gigi Sensodyne Fresh Mint 100ml", categoryMap.get("Makanan"), 35000, 150, "Pasta gigi khusus gigi sensitif dengan rasa mint segar dan perlindungan 24 jam", true, LocalDate.now(), "admin", "admin"),
            new Product(null, "Minyak Goreng Bimoli 2L", categoryMap.get("Makanan"), 45000, 200, "Minyak goreng kelapa sawit dengan kandungan nutrisi seimbang dan titik didih tinggi", true, LocalDate.now(), "admin", "admin"),
            new Product(null, "Tepung Terigu Segitiga Biru 1kg", categoryMap.get("Makanan"), 25000, 180, "Tepung terigu berkualitas tinggi untuk berbagai jenis roti, kue, dan pasta", true, LocalDate.now(), "admin", "admin"),
            new Product(null, "Susu UHT Indomilk Full Cream 1L", categoryMap.get("Makanan"), 18000, 250, "Susu sapi segar UHT dengan kandungan protein kalsium tinggi untuk kesehatan tulang", true, LocalDate.now(), "admin", "admin"),
            new Product(null, "Madu Asli Sumbawa 500ml", categoryMap.get("Makanan"), 150000, 60, "Madu asli dari pulau Sumbawa dengan rasa manis alami dan kandungan antioksidan tinggi", true, LocalDate.now(), "admin", "admin"),
            new Product(null, "Gula Pasir Gulaku 1kg", categoryMap.get("Makanan"), 15000, 300, "Gula pasir putih berkualitas dari tebu pilihan dengan kristal halus dan bersih", true, LocalDate.now(), "admin", "admin"),
            new Product(null, "Kacang Almond California 200g", categoryMap.get("Makanan"), 95000, 75, "Kacang almond panggang asal California kaya protein, serat, dan vitamin E", true, LocalDate.now(), "admin", "admin"),
            new Product(null, "Teh Celup Sosro Kotak 250ml x 10", categoryMap.get("Makanan"), 25000, 200, "Teh celup kemasan kotak praktis dengan rasa teh hitam pekat dan segar", true, LocalDate.now(), "admin", "admin"),
            new Product(null, "Beras Putih Pandan Wangi 5kg", categoryMap.get("Makanan"), 65000, 150, "Beras putih premium dengan aroma wangi pandan alami dan tekstur pulen saat dimasak", true, LocalDate.now(), "admin", "admin"),
            new Product(null, "Kedelai Hijau Kupas 500g", categoryMap.get("Makanan"), 25000, 100, "Kedelai hijau segar tanpa kulit siap diolah menjadi berbagai hidangan sehat", true, LocalDate.now(), "admin", "admin"),
            new Product(null, "Mie Instan Indomie Rasa Soto 5 Pcs", categoryMap.get("Makanan"), 12500, 500, "Mie instan rasa soto ayam dengan kuah kuning gurih dan tekstur kenyal khas", true, LocalDate.now(), "admin", "admin"),
            new Product(null, "Kacang Panjang Segar 1kg", categoryMap.get("Makanan"), 20000, 200, "Sayuran kacang panjang segar tanpa pestisida dengan tekstur renyah dan rasa manis", true, LocalDate.now(), "admin", "admin"),
            new Product(null, "Ikan Tuna Kaleng 185g", categoryMap.get("Makanan"), 35000, 120, "Ikan tuna kaleng dalam air dengan protein tinggi dan praktis untuk hidangan cepat saji", true, LocalDate.now(), "admin", "admin"),
            new Product(null, "Kerupuk Udang Super 200g", categoryMap.get("Makanan"), 45000, 150, "Kerupuk udang premium dengan ukuran besar dan rasa udang asli yang kuat", true, LocalDate.now(), "admin", "admin"),
            new Product(null, "Saus Tomat ABC 340ml", categoryMap.get("Makanan"), 25000, 300, "Saus tomat botol kaca dengan rasa segar dan manis dari tomat pilihan", true, LocalDate.now(), "admin", "admin"),
            new Product(null, "Kacang Mete Oven Roast 150g", categoryMap.get("Makanan"), 120000, 80, "Kacang mete panggang oven premium dengan tekstur renyah dan perpaduan gurih-pedas", true, LocalDate.now(), "admin", "admin"),
            new Product(null, "Tahu Putih Segar 500g", categoryMap.get("Makanan"), 15000, 180, "Tahu putih segar dengan tekstur lembut dan porsi protein nabati tinggi", true, LocalDate.now(), "admin", "admin"),
            new Product(null, "Tempe Murni Kedelai 200g", categoryMap.get("Makanan"), 5000, 250, "Tempe fermentasi tradisional dengan kandungan protein tinggi dan rasa gurih khas", true, LocalDate.now(), "admin", "admin"),
            new Product(null, "Kacang Hijau Kupas 500g", categoryMap.get("Makanan"), 20000, 150, "Kacang hijau kupas siap rebus untuk bubur kacang hijau atau kolak", true, LocalDate.now(), "admin", "admin"),
            new Product(null, "Telur Ayam Ras Segar 1kg", categoryMap.get("Makanan"), 30000, 250, "Telur ayam ras segar dengan cangkang bersih dan kuning telur oranye kaya nutrisi", true, LocalDate.now(), "admin", "admin"),
            new Product(null, "Kentang Goreng Beku 1kg", categoryMap.get("Makanan"), 55000, 120, "Kentang goreng beku siap goreng dengan tekstur renyah di luar dan lembut di dalam", true, LocalDate.now(), "admin", "admin"),
            new Product(null, "Jagung Pipil 500g", categoryMap.get("Makanan"), 15000, 200, "Jagung pipil segar siap olah untuk bubur jagung atau camilan gurih", true, LocalDate.now(), "admin", "admin"),
            new Product(null, "Kedelai Kering 1kg", categoryMap.get("Makanan"), 35000, 150, "Kedelai kering utuh dengan kandungan protein tinggi untuk produk kedelai buatan sendiri", true, LocalDate.now(), "admin", "admin")
        };

        Product[] pakaianProducts = {
            new Product(null, "Kaos Oblong Cotton Combed 30s Basic", categoryMap.get("Pakaian"), 75000, 200, "Kaos katun premium 30s dengan jahitan rantai dan potongan regular fit", true, LocalDate.now(), "admin", "admin"),
            new Product(null, "Celana Jeans Pria Slim Fit Wrangler", categoryMap.get("Pakaian"), 185000, 150, "Celana jeans pria potongan slim fit dengan bahan denim stretch premium", true, LocalDate.now(), "admin", "admin"),
            new Product(null, "Kemeja Flanel Pria Kotak-kotak", categoryMap.get("Pakaian"), 125000, 120, "Kemeja flanel lengan pendek motif kotak-kotak klasik dengan potongan casual", true, LocalDate.now(), "admin", "admin"),
            new Product(null, "Jaket Denim Wanita Cropped Fit", categoryMap.get("Pakaian"), 225000, 100, "Jaket denim wanita potongan pendek dengan desain klasik dan kancing vintage", true, LocalDate.now(), "admin", "admin"),
            new Product(null, "Dress Wanita Maxi Flowy Musim Panas", categoryMap.get("Pakaian"), 185000, 85, "Dress maxi wanita flowy dengan bahan katun rayon ringan dan motif floral", true, LocalDate.now(), "admin", "admin"),
            new Product(null, "Celana Chino Pria Slim Tapered", categoryMap.get("Pakaian"), 165000, 130, "Celana chino pria potongan slim tapered dengan pinggang elastis dan bahan katun premium", true, LocalDate.now(), "admin", "admin"),
            new Product(null, "Blouse Wanita Kerutan Wajah Kucing", categoryMap.get("Pakaian"), 95000, 110, "Blouse wanita dengan detail kerutan wajah kucing dan potongan loose fit feminin", true, LocalDate.now(), "admin", "admin"),
            new Product(null, "Jaket Parka Musim Dingin Tebal", categoryMap.get("Pakaian"), 350000, 75, "Jaket parka musim dingin dengan lapisan bulu sintetis dan tahan air premium", true, LocalDate.now(), "admin", "admin"),
            new Product(null, "Celana Legging Wanita Yoga Stretch", categoryMap.get("Pakaian"), 85000, 180, "Celana yoga wanita stretch tinggi dengan pinggang karet lebar dan bahan breathable", true, LocalDate.now(), "admin", "admin"),
            new Product(null, "Kemeja Batik Modern Pria Lengan Pendek", categoryMap.get("Pakaian"), 155000, 90, "Kemeja batik pria modern lengan pendek dengan motif kontemporer dan potongan casual", true, LocalDate.now(), "admin", "admin"),
            new Product(null, "Rok Mini Wanita Plisket Kotak-kotak", categoryMap.get("Pakaian"), 75000, 140, "Rok mini wanita potongan plisket motif kotak-kotak klasik dengan elastis pinggang", true, LocalDate.now(), "admin", "admin"),
            new Product(null, "Sweater Rajut Kasual Unisex", categoryMap.get("Pakaian"), 120000, 110, "Sweater rajut unisex dengan pola timbul klasik dan potongan oversized cozy", true, LocalDate.now(), "admin", "admin"),
            new Product(null, "Celana Pendek Denim Wanita High Waist", categoryMap.get("Pakaian"), 125000, 95, "Celana pendek denim wanita potongan high waist dengan pinggang karet belakang", true, LocalDate.now(), "admin", "admin"),
            new Product(null, "Atasan Wanita Off-shoulder Ruffle", categoryMap.get("Pakaian"), 85000, 125, "Atasan wanita off-shoulder dengan detail ruffle dan potongan loose fit flowy", true, LocalDate.now(), "admin", "admin"),
            new Product(null, "Jaket Kulit Sintetis Motor Kasual", categoryMap.get("Pakaian"), 275000, 80, "Jaket kulit sintetis motor dengan lining katun dan perlindungan bahu siku", true, LocalDate.now(), "admin", "admin"),
            new Product(null, "Celana Palazzo Wanita Flowy", categoryMap.get("Pakaian"), 115000, 100, "Celana palazzo wanita flowy dengan bahan katun rayon ringan dan potongan wide leg", true, LocalDate.now(), "admin", "admin"),
            new Product(null, "Kemeja Wanita Lengan Panjang Button Down", categoryMap.get("Pakaian"), 105000, 135, "Kemeja wanita lengan panjang button down dengan kerah klasik dan potongan slim fit", true, LocalDate.now(), "admin", "admin"),
            new Product(null, "Jaket Hoodie Kasual Pria O-neck", categoryMap.get("Pakaian"), 145000, 120, "Jaket hoodie pria dengan kantong kangaroo dan karet manset serta pinggang elastis", true, LocalDate.now(), "admin", "admin"),
            new Product(null, "Celana Jeans Wanita Boyfriend Fit", categoryMap.get("Pakaian"), 165000, 85, "Celana jeans wanita potongan boyfriend longgar dengan robekan estetik dan raw hem", true, LocalDate.now(), "admin", "admin"),
            new Product(null, "Dress Wanita Shirt Dress Kantor", categoryMap.get("Pakaian"), 135000, 90, "Dress wanita model shirt dress kantor dengan potongan rapi dan tali pinggang sepat", true, LocalDate.now(), "admin", "admin"),
            new Product(null, "Celana Jogger Pria Premium", categoryMap.get("Pakaian"), 115000, 150, "Celana jogger pria dengan pinggang karet tali dan manset kaki rib elastis premium", true, LocalDate.now(), "admin", "admin"),
            new Product(null, "Atasan Crop Top Wanita Ruffle", categoryMap.get("Pakaian"), 65000, 160, "Crop top wanita dengan detail ruffle dada dan potongan pas tubuh trendy", true, LocalDate.now(), "admin", "admin"),
            new Product(null, "Jaket Bomber Wanita Nyaman", categoryMap.get("Pakaian"), 125000, 100, "Jaket bomber wanita dengan potongan modern dan elastis pergelangan tangan serta pinggang", true, LocalDate.now(), "admin", "admin"),
            new Product(null, "Celana Kulot Wanita Formal", categoryMap.get("Pakaian"), 145000, 75, "Celana kulot wanita potongan formal dengan bahan katun premium dan drape elegan", true, LocalDate.now(), "admin", "admin"),
            new Product(null, "Kemeja Wanita Flanel Kasual Musim Gugur", categoryMap.get("Pakaian"), 135000, 110, "Kemeja flanel wanita lengan panjang motif kotak-kotak kasual dengan potongan oversize", true, LocalDate.now(), "admin", "admin")
        };

        Arrays.stream(elektronikProducts).forEach(productRepository::save);
        Arrays.stream(bukuProducts).forEach(productRepository::save);
        Arrays.stream(makananProducts).forEach(productRepository::save);
        Arrays.stream(pakaianProducts).forEach(productRepository::save);

        System.out.println("100 produk telah diinisialisasi ke database!");
        System.out.println("- 25 produk Elektronik");
        System.out.println("- 25 produk Buku");
        System.out.println("- 25 produk Makanan");
        System.out.println("- 25 produk Pakaian");
    }
}