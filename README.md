
# MCBanners/mc-api

Service designed to interact with Minecraft Servers and return JSON data on the status of the server.

## API Reference

Base URL: https://api.mcbanners.com

#### Supported Platforms (and their parameter type)
spigot -> number\
sponge -> string\
curseforge -> number


#### Check valid author

```http
  GET /author/${platform}/${id}/isValid
```

| Parameter  | Type                 | Description                            |
|:-----------|:---------------------|:---------------------------------------|
| `platform` | `string`             | Platform type from the above list      |
| `id`       | `string` or `number` | The id in the form from the above list |

### Get author banner

```http
  GET /author/${platform}/${id}/banner.${outputType}
```

| Parameter    | Type                 | Description                            |
|:-------------|:---------------------|:---------------------------------------|
| `platform`   | `string`             | Platform type from the above list      |
| `id`         | `string` or `number` | The id in the form from the above list |
| `outputType` | `string`             | Either `png` or `jpg`                  |

#### Check valid resource

```http
  GET /resource/${platform}/${id}/isValid
```

| Parameter  | Type                 | Description                            |
|:-----------|:---------------------|:---------------------------------------|
| `platform` | `string`             | Platform type from the above list      |
| `id`       | `string` or `number` | The id in the form from the above list |

### Get resource banner

```http
  GET /resource/${platform}/${id}/banner.${outputType}
```

| Parameter    | Type                 | Description                            |
|:-------------|:---------------------|:---------------------------------------|
| `platform`   | `string`             | Platform type from the above list      |
| `id`         | `string` or `number` | The id in the form from the above list |
| `outputType` | `string`             | Either `png` or `jpg`                  |

### Check valid server

```http
  GET /server/${host}/${port}/isValid
```

| Parameter | Type     | Description     |
|:----------|:---------|:----------------|
| `host`    | `string` | The server ip   |
| `port`    | `number` | The server port |

### Get server banner

```http
  GET /server/${host}/${port}/banner.${outputType}
```

| Parameter    | Type     | Description           |
|:-------------|:---------|:----------------------|
| `host`       | `string` | The server ip         |
| `port`       | `number` | The server port       |
| `outputType` | `string` | Either `png` or `jpg` |


