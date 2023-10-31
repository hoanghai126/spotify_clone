package com.example.android.di.module

import com.example.android.di.scope.FragmentScoped
import com.example.android.ui.bottom_nav_fragment.ProfileFragment
import com.example.android.ui.fragment.bottom_nav.*
import com.example.android.ui.fragment.login.*
import com.example.android.ui.fragment.playing_music.LyricPageFragment
import com.example.android.ui.fragment.playing_music.MusicPageFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
@Suppress("UNUSED")
internal abstract class AppFragmentModule {

    @FragmentScoped
    @ContributesAndroidInjector
    internal abstract fun contributeGetStartedFragment(): GetStartedFragment

    @FragmentScoped
    @ContributesAndroidInjector
    internal abstract fun contributeLoginFragment(): LoginFragment

    @FragmentScoped
    @ContributesAndroidInjector
    internal abstract fun contributeRegisterFragment(): RegisterFragment

    @FragmentScoped
    @ContributesAndroidInjector
    internal abstract fun contributeSignUpFragment(): SignUpFragment

    @FragmentScoped
    @ContributesAndroidInjector
    internal abstract fun contributeContinueFragment(): ContinueFragment

    @FragmentScoped
    @ContributesAndroidInjector
    internal abstract fun contributeHomepageFragment(): HomepageFragment

    @FragmentScoped
    @ContributesAndroidInjector
    internal abstract fun contributeLibraryFragment(): LibraryFragment

    @FragmentScoped
    @ContributesAndroidInjector
    internal abstract fun contributeArtistFragment(): ArtistFragment

    @FragmentScoped
    @ContributesAndroidInjector
    internal abstract fun contributeArtistInfoFragment(): ArtistInfoFragment

    @FragmentScoped
    @ContributesAndroidInjector
    internal abstract fun contributeLyricPageFragment(): LyricPageFragment

    @FragmentScoped
    @ContributesAndroidInjector
    internal abstract fun contributeMusicPageFragment(): MusicPageFragment

    @FragmentScoped
    @ContributesAndroidInjector
    internal abstract fun contributeProfileFragment(): ProfileFragment

    @FragmentScoped
    @ContributesAndroidInjector
    internal abstract fun contributeHistoryFragment(): HistoryFragment

    @FragmentScoped
    @ContributesAndroidInjector
    internal abstract fun contributeAlbumFragment(): AlbumFragment

    @FragmentScoped
    @ContributesAndroidInjector
    internal abstract fun contributePodcastFragment(): PodcastFragment

    @FragmentScoped
    @ContributesAndroidInjector
    internal abstract fun contributeGenresFragment(): GenresFragment



}

