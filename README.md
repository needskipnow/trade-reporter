# trade-reporter
Trade reporter app for Vanguard job application coding challenge

TradeEventController exposes two REST api endpoints:
- /trade/events         Accepts multiple events xml payload 
- /trade/load-events    Loads xml events payload from the file system

Events are processed using parallel streams while records persisted in async mode to facilitate non-blocking event processing

# Execute tests: ./gradlew clean test

# Run spring-boot app: ./gradlew bootRun


