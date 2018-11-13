# SISTEM INFORMASI RAWAT INAP


## API 
- Web Service untuk Pendaftaran Rawat Inap dari Sistem Informasi IGD
  Form Request: POST, /api/daftar-ranap
  Response Page: /api/daftar-ranap
  Role: Admin IGD
  request body
```
{
  "id"      :"1",
  "nama"    :"dora",
  "key"    :"10"
}
```
response example
```
{
  "status":"200"
}
```

- Web Service untuk Mengembalikan Daftar Pasien Rawat Inap
  Form Request: GET, /api/get-all-kamar
  Response Page: /api/get-all-kamar
  Role: -
  response example
```
[
  {
    "id"      :"1",
    "nama"    :"dora",
    "status"  :"mendaftar di igd"
  },
  {

    "id"      :"2",
    "nama"    :"boots",
    "status"  :"mendaftar langsung"
  },
  {

    "id"      :"3",
    "nama"    :"sweeper",
    "status"  :"mendaftar di rawat inap"
  }
]
```
