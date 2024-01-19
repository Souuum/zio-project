package services

import entities._
import zio._
import zio.Console._
import java.io.IOException
import scala.collection.mutable.ListBuffer
import repositories.implementation.ArtistRepository

object ArtistService {

  def callGetAllArtists(): ZIO[Artist, IOException, ListBuffer[Artist]] = {
    return ArtistRepository.getAll()
  }

  def callGetArtistById(id: String): Option[Artist] = {
    return ArtistRepository.getById(id)
  }
//trie les artistes par popularité
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

  def test(): Any = {
    return ArtistRepository.test()
  }

}
