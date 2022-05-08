package iostudio.`in`.topmovies.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import iostudio.`in`.topmovies.repositories.IMovieDetailRepository
import iostudio.`in`.topmovies.repositories.IPopularMoviesRepository
import iostudio.`in`.topmovies.repositories.MovieDetailRepository
import iostudio.`in`.topmovies.repositories.PopularMoviesRepository
import javax.inject.Singleton

@InstallIn(ViewModelComponent::class)
@Module
class RepositoryModule {
    @Provides
    fun providePopularMoviesRepository(popularMoviesRepository: PopularMoviesRepository): IPopularMoviesRepository =
        popularMoviesRepository

    @Provides
    fun provideMovieDetailsRepository(movieDetailsRepository: MovieDetailRepository): IMovieDetailRepository =
        movieDetailsRepository

}