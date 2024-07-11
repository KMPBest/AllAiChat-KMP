import SwiftUI
import ComposeApp

@main
struct iOSApp: App {
  init() {
      AppInitializer.shared.initialize(onKoinStart: { _ in })
  }
	var body: some Scene {
		WindowGroup {
			ContentView()
		}
	}
}
