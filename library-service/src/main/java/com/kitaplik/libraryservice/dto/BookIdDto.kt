package com.kitaplik.libraryservice.dto

data class BookIdDto @JvmOverloads constructor(
    val bookId: String? = "",
    val isbn: String? = ""
){
    companion object{
        @JvmStatic
        fun convert(id: String,isbn: String): BookIdDto {
            return BookIdDto(id,isbn)

            /**"convert" fonksiyonu, iki parametre alır: "id" ve "isbn". Bu fonksiyon, "BookIdDto" tipinde bir nesne
            döndürür ve "id" ve "isbn" parametrelerini bu nesneye atar.
            Bu şekilde, "companion object" içindeki "convert" fonksiyonu, "BookIdDto" nesnesi oluşturmak için
            kullanılabilir ve bu nesne, "id" ve "isbn" bilgilerini almak için kullanılabilir.**/
        }
    }
}
