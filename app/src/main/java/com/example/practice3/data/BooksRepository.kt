package com.example.practice3.data

import com.example.practice3.models.Book

class BooksRepository {
    public fun getAll() : List<Book> {
        return data;
    }

    public fun get(id: Int) : Book? {
        return data.find {it.id == id}
    }

    companion object {
        val data = listOf(
            Book(1, "Prince Harry", "Elizabeth Krajnik", "Greenhaven Publishing LLC", "2019-07-15",
                "Prince Harry's life has taken him far from the traditional path many members of the British royal family followed before him. From the death of his mother at a young age and his tumultuous early adult years to his military service and charity work, his journey from precocious prince to activist has inspired many people around the world. Readers explore his life story, including his marriage to American actress Meghan Markle. Detailed main text, fact-filled sidebars, annotated quotes, and vibrant photographs offer a compelling biographical treatment. How did Prince Harry become known as a royal rule-breaker? Readers will enjoy finding the answer.",
                "en"
            ),
            Book(
                2, "Wild About Harry", "Bateman", "Hachette UK", "2013-05-09",
                "Meet Harry McKee - a sleazy local chat show host. Once a loving husband, he's become a drunken unfaithful slob. His wife is divorcing him and taking him to the cleaners. Even his kids won't speak to him. On his last night as a married man he winds up drunk and is beaten up. When he keels over the next day at the divorce hearing his wife and solicitor assume he's pulling a fast one. He eventually wakes from a week-long coma but he's lost his memory - everything since 1974. Inside his sagging middle-aged body, Harry feels eighteen again. Though he doesn't know it yet, he has been given the chance to get back his life, his wife and his self-respect. If only he could remember how it all went wrong and why his family hate him...",
                "en"
            ),
            Book(
                3, "Harry White and the American Creed", "James M. Boughton", "Yale University Press", "2021-11-30",
                "The life of a major figure in twentieth‑century economic history whose impact has long been clouded by dubious allegations Although Harry Dexter White (1892–1948) was arguably the most important U.S. government economist of the twentieth century, he is remembered more for having been accused of being a Soviet agent. During the Second World War, he became chief advisor on international financial policy to Secretary of the Treasury Henry Morgenthau, a role that would take him to Bretton Woods, where he would make a lasting impact on the architecture of postwar international finance. However, charges of espionage, followed by his dramatic testimony before the House Un‑American Activities Committee and death from a heart attack a few days later, obscured his importance in setting the terms for the modern global economy. In this book, James Boughton rehabilitates White, delving into his life and work and returning him to a central role as the architect of the world’s financial system.",
                "en"
            )
        )
    }
}