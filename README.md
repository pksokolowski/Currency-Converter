# Building instructions

## secret.properties file
The project relies on a secret.properties file in the root dir of the project. Please feel free
to use the below template:

```
# API key for api.exchangeratesapi.io service
CURRENCY_EXCHANGE_API_KEY="yourApiKeyGoesHere"
```

# Note on remote api use
Due to strict limits of api usage, the app can be build in two flavours:
"remoteApiRates" and "mockRates", using real and mock exchange rates respectively.