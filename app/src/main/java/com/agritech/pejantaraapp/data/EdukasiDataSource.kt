package com.agritech.pejantaraapp.data

object EdukasiDataSource {
    val articles: List<Article> = listOf(
        Article(
            title = "Kelola Sampah dengan Metode Pilah, Pilih dan Pulih",
            description = "Jumlah sampah plastik diIndonesia relatif tinggi saat ini menjadi salah satu tantangan yang harus dihadapi dan harus segera dipecahkan agar Indonesia menjadi negara bebas sampah.",
            url = "https://sohib.indonesiabaik.id/article/cara-mengurangi-sampah-plastik-CKZxS",
            imageUrl = "https://sohib.indonesiabaik.id/thumbnail/article-730/articles/2022/11/18/cara-mengurangi-sampah-plastik-djjCSX0ARK.jpg"
        ),
        Article(
            title = "Pentingnya Daur Ulang Sampah Bagi Masa Depan Lingkungan Yang Lebih Baik",
            description = "Sampah adalah barang atau benda yang dibuang karena tidak terpakai lagi dan sebagainya, seperti kotoran, daun, kertas, plastik,dan lain-lain.",
            url = "https://environment-indonesia.com/tips-hemat-kertas-demi-lingkungan/",
            imageUrl = "https://environment-indonesia.com/wp-content/uploads/2015/06/best-ways-to-save-paper.jpg.webp"
        ),
        Article(
            title = "Berbagai Penyakit Akibat Kebiasaan Buang Sampah Sembarangan",
            description = "Selain merusak pemandangan, kebiasaan buang sampah sembarangan juga dapat menimbulkan penyakit.",
            url = "https://www.colorado.edu/ecenter/2021/02/09/ways-reuse-cardboard-and-why-it-important",
            imageUrl = "https://www.colorado.edu/ecenter/sites/default/files/styles/large/public/callout/cardboard_bales.png?itok=bvykKAYP"
        ),
        Article(
            title = "Ancaman Nyata Sampah Terhadap Satwa di Alam",
            description = "Keberadaan sampah jika tidak ditangani dengan baik memang kerapkali mendatangkan masalah. ",
            url = "https://indonesiasustainability.com/reduce-reuse-recycle-adalah-contohnya/",
            imageUrl = "https://umumsetda.bulelengkab.go.id/public/uploads/konten/3r-reuse-reduce-recycle-sampah-49.jpg"
        ),
        Article(
            title = "Solusi Asyik, Kurangi Sampah Plastik",
            description = "Kesadaran untuk menghindari penggunaan plastik secara berlebihan menjadi salah satu solusinya. Dimulai dengan memperhatikan penggunaan plastik diri sendiri.",
            url = "https://indonesiasustainability.com/reduce-reuse-recycle-adalah-contohnya/",
            imageUrl = "https://indonesiasustainability.com/wp-content/uploads/2022/07/Reduce-Reuse-Recycle-Adalah-Pengertian-Dan-Contohnya-1.jpg"
        ),
    )
}

data class Article(
    val title: String,
    val description: String,
    val url: String,
    val imageUrl: String
)
