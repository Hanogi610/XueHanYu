# XueHanYu: Chinese Story Reader App

## Sprint-Based Project Plan (Parallelized & Detailed)

This plan breaks down each sprint into two parallel tracks (A & B) so two developers can work efficiently. Each task is actionable and easy to follow. Acceptance criteria are provided for each sprint.

---

### **Sprint 1: Project Setup & Authentication**

**Track A: Project Foundation**
- [ ] Initialize Android project with Clean Architecture, MVVM, Hilt, Compose, and Navigation
- [ ] Set up project structure: core, auth, story, ui, component, data, domain, di
- [ ] Integrate Firebase project and add google-services plugin
- [ ] Configure Hilt for dependency injection

**Track B: Authentication UI & Flow**
- [ ] Design and implement Login and Sign Up screens (stateless composables)
- [ ] Add email/password and Google sign-in UI
- [ ] Connect UI to ViewModel and repository
- [ ] Add error handling and user feedback (Toasts)
- [ ] Add dark mode support (Material 3 theme, toggle)

**Acceptance Criteria:**
- User can sign up, log in, and log out
- Auth state persists across app restarts
- UI supports both light and dark themes
- All navigation between Auth screens works

---

### **Sprint 2: Story Library (List & Search)**

**Track A: Data & Repository**
- [ ] Design data model for stories (title, level, description, cover image, content, audio, etc.)
- [ ] Implement repository for fetching stories (local and remote)
- [ ] Seed sample stories in Firebase or local assets

**Track B: UI & Interaction**
- [ ] Implement story list UI (LazyColumn)
- [ ] Add filter by difficulty (Beginner, Intermediate, Advanced)
- [ ] Add search bar for stories
- [ ] Display story title, level, description, and cover image

**Acceptance Criteria:**
- User can browse, filter, and search stories
- Story data loads from local or remote source
- UI is responsive and visually consistent

---

### **Sprint 3: Story Reader (Core Reading Experience)**

**Track A: Reader Functionality**
- [ ] Implement sentence-by-sentence view (Chinese text, pinyin, English translation)
- [ ] Add toggle for showing/hiding translation
- [ ] Implement tap-to-show word popup with definitions

**Track B: Reader UI & Navigation**
- [ ] Add full-screen reading mode
- [ ] Implement navigation from story list to reader
- [ ] Add bookmarks/continue reading feature

**Acceptance Criteria:**
- User can read stories sentence by sentence
- User can toggle translations and view word definitions
- User can bookmark and resume stories

---

### **Sprint 4: Audio Playback & Vocabulary Builder**

**Track A: Audio Playback**
- [ ] Integrate native audio for each sentence/story
- [ ] Add play/pause controls in reader
- [ ] Implement sentence highlighting synced with audio (karaoke-style)

**Track B: Vocabulary Builder**
- [ ] Allow users to save/favorite words from stories
- [ ] Implement word detail view (pinyin, translation, example)
- [ ] Add flashcard/review mode (optional spaced repetition)

**Acceptance Criteria:**
- User can play/pause audio for stories
- Sentence highlighting syncs with audio playback
- User can save words and review them as flashcards

---

### **Sprint 5: Offline Support & User Features**

**Track A: Offline Support**
- [ ] Enable downloading of stories and audio for offline reading
- [ ] Handle offline/online state gracefully in UI
- [ ] Sync saved words and bookmarks when online

**Track B: User Features**
- [ ] Add reading history tracking
- [ ] Implement daily reading reminder (notifications)
- [ ] Add goal and reminder notification system
- [ ] Add dark mode toggle in settings

**Acceptance Criteria:**
- User can read downloaded stories and listen to audio offline
- User receives reminders and can track reading history
- App handles offline/online transitions smoothly

---

### **Sprint 6: Advanced Features & Polish**

**Track A: Advanced Features**
- [ ] Support reading user PDF files (import and parse)
- [ ] (Optional) Webview for translator of user-given links

**Track B: UI/UX Polish**
- [ ] Polish UI/UX (animations, transitions, accessibility)
- [ ] Optimize performance and fix bugs
- [ ] Update documentation and README

**Acceptance Criteria:**
- User can import and read their own PDFs
- User can open a translator webview for any link
- App feels smooth, modern, and accessible

---

## Notes & Priorities
- Each sprint is split into two tracks for parallel work.
- Authentication and story library are foundational; prioritize stability and UX here.
- Audio, vocabulary, and offline support are core differentiators—focus on user experience.
- Advanced features (PDF, webview) are optional and can be scheduled after core app is stable.

---

> Use this plan to guide development, assign tasks, and track progress. Adjust sprint scope as needed for your team and timeline. 