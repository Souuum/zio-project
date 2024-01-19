package services

import entities._
import zio._
import zio.Console._
import java.io.IOException
import scala.collection.mutable.ListBuffer
import repositories.implementation.ArtistRepository

object ArtisteService {

//trie les albums par popularité
  def callSortArtistsPopularityASC()
      : ZIO[Artist, IOException, ListBuffer[Artist]] = {
    return ArtistRepository.getAllByAscPopularity()
  }

  def callSortArtistsPopularityDESC()
      : ZIO[Artist, IOException, ListBuffer[Artist]] = {
    return ArtistRepository.getAllByDescPopularity()
  }

  def callSortByGenre(
      genre: String
  ): ZIO[Artist, IOException, ListBuffer[Artist]] = {
    return ArtistRepository.getByGenre(genre)
  }

}
