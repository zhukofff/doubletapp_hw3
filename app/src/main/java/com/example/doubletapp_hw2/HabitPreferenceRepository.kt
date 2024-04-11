package com.example.doubletapp_hw2

import androidx.datastore.core.DataStore
import java.util.prefs.Preferences
//import androidx.datastore.preferences.core.*


/*private object PreferencesKeys {
    val ENABLE_AUTO_ADDITION = booleanPreferencesKey("button_state")
    val BASIC_LANGUAGE = stringPreferencesKey("basic_language")
    val ICON_BASIC_LANGUAGE = intPreferencesKey("icon_basic_language")
    val ICON_LEARNING_LANGUAGE = intPreferencesKey("icon_learning_language")
    val ALARMS = stringPreferencesKey("alarms")
    val LEARNING_LANGUAGE = stringPreferencesKey("learning_language")
    val ENABLE_ALARM = booleanPreferencesKey("enable_alarm")

}
class HabitPreferenceRepository (
    private val userPreferencesStore: DataStore<Preferences>
) {
      val userPreferencesStateFlow: Flow<UserPreferences> = userPreferencesStore.data
          .catch {exception ->
              if (exception is IOException)
                  emit(emptyPreferences())
              else
                  throw exception
          }
          .map { preferences ->
              Log.v("User pref", "enable auto addition = ${preferences[PreferencesKeys.ENABLE_AUTO_ADDITION]}, enable alarm = ${preferences[PreferencesKeys.ENABLE_ALARM] }")
              UserPreferences(
                  alarm = preferences[PreferencesKeys.ALARMS]  ?: "20 min." ,
                  basicLanguage = preferences[PreferencesKeys.BASIC_LANGUAGE] ?: Language.Russian.name ,
                  iconBasicLanguage = preferences[PreferencesKeys.ICON_BASIC_LANGUAGE] ?: R.drawable.ic_russian2 ,
                  learningLanguage = preferences[PreferencesKeys.LEARNING_LANGUAGE] ?: Language.English.name ,
                  iconLearningLanguage = preferences[PreferencesKeys.ICON_LEARNING_LANGUAGE] ?: R.drawable.ic_english,
                  enableAutoAddition = preferences[PreferencesKeys.ENABLE_AUTO_ADDITION] ?: true,
                  enableAlarm = preferences[PreferencesKeys.ENABLE_ALARM] ?: false
              )

          }

      suspend fun setUserSettings(user: UserPreferences) {
          userPreferencesStore.edit { preferences ->
              preferences[PreferencesKeys.ALARMS] = user.alarm
              preferences[PreferencesKeys.ICON_BASIC_LANGUAGE] = user.iconBasicLanguage
              preferences[PreferencesKeys.BASIC_LANGUAGE] = user.basicLanguage
              preferences[PreferencesKeys.LEARNING_LANGUAGE] = user.learningLanguage
              preferences[PreferencesKeys.ICON_LEARNING_LANGUAGE] = user.iconLearningLanguage
              preferences[PreferencesKeys.ENABLE_AUTO_ADDITION] = user.enableAutoAddition!!
              preferences[PreferencesKeys.ENABLE_ALARM] = user.enableAlarm
          }
      }
}*/

