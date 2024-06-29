import SwiftUI
import ComposeApp

@main
struct iOSApp: App {
  init() {
      KoinKt.doInitKoin(onKoinStart: { _ in })
  }
	var body: some Scene {
		WindowGroup {
			ContentView()
		}
	}
}
