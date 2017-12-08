--
-- Estructura de tabla para la tabla imagenesPublicaciones
--

--drop table dbo.imagenesPublicaciones

use A_Interactivas_01

CREATE TABLE dbo.imagenesPublicaciones (
  nroImagen int IDENTITY(1,1) NOT NULL,
  nroPublicacion int NOT NULL,
  imagenURL varchar(256) NOT NULL
)

alter table dbo.imagenesPublicaciones
add CONSTRAINT PK_PROJECT PRIMARY KEY CLUSTERED (nroImagen)

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla movCtaCte
--

CREATE TABLE dbo.movCtaCte (
  nroMovimiento int IDENTITY(1,1) NOT NULL,
  nombreDeUsuario varchar(20) NOT NULL,
  monto float NOT NULL,
  concepto int NOT NULL,
  nroVenta int NOT NULL,
  fechaMovimiento datetime NOT NULL
)

alter table dbo.movCtaCte
add CONSTRAINT PK_movCtaCte PRIMARY KEY CLUSTERED (nroMovimiento)

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla movTipo
--

CREATE TABLE dbo.movTipo (
  concepto int NOT NULL,
  descripcion varchar(255) NOT NULL
)

alter table dbo.movTipo
add CONSTRAINT PK_movTipo PRIMARY KEY CLUSTERED (concepto)

--
-- Volcado de datos para la tabla movTipo
--

INSERT INTO movTipo (concepto, descripcion) VALUES,
(1, 'COMPRA'),
(0, 'VARIOS'),
(2, 'VENTA')
(3, 'COMISION');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla ofertas
--

CREATE TABLE dbo.ofertas (
  nroOferta int IDENTITY(1,1) NOT NULL,
  monto float NOT NULL,
  medioDePago varchar(30) DEFAULT NULL,
  fechaOferta datetime DEFAULT NULL,
  nroPublicacion int NOT NULL,
  nombreDeUsuario varchar(20) DEFAULT NULL
)

alter table dbo.ofertas
add CONSTRAINT PK_ofertas PRIMARY KEY CLUSTERED (nroOferta)

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla parametros
--

CREATE TABLE dbo.parametros (
  tabla varchar(16) NOT NULL,
  clave varchar(16) NOT NULL,
  descripcion varchar(255) NOT NULL,
  valor1 varchar(20) NOT NULL,
  valor2 varchar(20) NOT NULL
)

alter table dbo.parametros
add CONSTRAINT PK_parametros PRIMARY KEY CLUSTERED (tabla,clave,valor1)

--
-- Volcado de datos para la tabla parametros
--

INSERT INTO parametros (tabla, clave, descripcion, valor1, valor2) VALUES
('PASSWORD', 'VENC', 'Días para vencimiento de password', '180', ' '),
('VENTA', 'COMISION', 'Porcentaje de comision cobrada sobre cada venta.', '10', '');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla publicaciones
--

CREATE TABLE dbo.publicaciones (
  nroPublicacion int IDENTITY(1,1) NOT NULL,
  nombreDeProducto varchar(64) NOT NULL,
  descripcion varchar(256) NOT NULL,
  fechaPublicacion datetime NOT NULL,
  precioPublicado float NOT NULL,
  estadoPublicacion varchar(15) NOT NULL,
  nombreDeUsuarioVendedor varchar(20) NOT NULL,
  stock int DEFAULT NULL,
  tipoPublicacion varchar(32) NOT NULL,
  fechaHasta datetime DEFAULT NULL,
  ultimaOferta int DEFAULT NULL
)

alter table dbo.publicaciones
add CONSTRAINT PK_publicaciones PRIMARY KEY CLUSTERED (nroPublicacion)


-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla usuarios
--

CREATE TABLE dbo.usuarios (
  nombreDeUsuario varchar(20) NOT NULL,
  nombre varchar(64) DEFAULT NULL,
  domicilio varchar(100) DEFAULT NULL,
  mail varchar(64) DEFAULT NULL,
  fechaCreacion datetime NOT NULL,
  passwordString varchar(30) NOT NULL,
  passwordModif datetime NOT NULL,
  saldoCtaCte float DEFAULT NULL,
  activo tinyint NOT NULL,
  administrador tinyint NOT NULL
)

alter table dbo.usuarios
add CONSTRAINT PK_usuarios PRIMARY KEY CLUSTERED (nombreDeUsuario)

--
-- Volcado de datos para la tabla usuarios
--

INSERT INTO usuarios (nombreDeUsuario, nombre, domicilio, mail, fechaCreacion, passwordString, passwordModif, saldoCtaCte, activo, administrador) VALUES
('admin', 'Administrador', 'Falso', 'Falso', '2017-10-08 18:45:55', 'admin', '2017-10-08 18:45:55', 0, 1, 1);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla ventas
--

CREATE TABLE dbo.ventas (
  nroVenta int IDENTITY(1,1) NOT NULL,
  medioDePago varchar(20) NOT NULL,
  cantidad int NOT NULL,
  nroPublicacion int NOT NULL,
  nombreDeUsuarioComprador varchar(20) NOT NULL,
  montoUnitario float NOT NULL,
  montoComision float NOT NULL,
  estadoPago varchar(15) NOT NULL,
  fechaDeCompra datetime NOT NULL,
  nroTarjeta varchar(30) DEFAULT NULL,
  CBU varchar(30) DEFAULT NULL
)

alter table dbo.ventas
add CONSTRAINT PK_ventas PRIMARY KEY CLUSTERED (nroVenta)


-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla calificaciones
--

--drop TABLE dbo.calificaciones;
CREATE TABLE dbo.calificaciones (
  numero int IDENTITY(1,1) NOT NULL,
  comprador varchar(20) NOT NULL,
  vendedor varchar(20) NOT NULL,
  puntuacion int DEFAULT NULL,
  comentarios varchar(255) DEFAULT NULL,
  pendiente tinyint NOT NULL,
  venta int NOT NULL,
  fechaCalificacion datetime DEFAULT NULL
)
;
alter table dbo.calificaciones
add CONSTRAINT PK_calificaciones PRIMARY KEY CLUSTERED (numero)





--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla imagenesPublicaciones
--
ALTER TABLE dbo.imagenesPublicaciones 
ADD constraint imagenesPublicaciones_FK foreign key (nroPublicacion) references publicaciones (nroPublicacion)

--
-- Indices de la tabla movCtaCte
--
ALTER TABLE dbo.movCtaCte ADD CONSTRAINT FK_nombreDeUsuario FOREIGN KEY (nombreDeUsuario) REFERENCES usuarios(nombreDeUsuario);
ALTER TABLE dbo.movCtaCte ADD CONSTRAINT FK_nroVenta FOREIGN KEY (nroVenta) REFERENCES ventas(nroVenta);
ALTER TABLE dbo.movCtaCte ADD CONSTRAINT FK_concepto FOREIGN KEY (concepto) REFERENCES movTipo(concepto);

--
-- Indices de la tabla movTipo
--
ALTER TABLE movTipo ADD constraint descripcion_2 UNIQUE (descripcion);

--
-- Indices de la tabla ofertas
--
ALTER TABLE dbo.ofertas ADD CONSTRAINT FK_nroPublicacion2 FOREIGN KEY (nroPublicacion) REFERENCES publicaciones(nroPublicacion);
ALTER TABLE dbo.ofertas ADD CONSTRAINT FK_nombreDeUsuario2 FOREIGN KEY (nombreDeUsuario) REFERENCES usuarios(nombreDeUsuario);

--
-- Indices de la tabla publicaciones
--
ALTER TABLE dbo.publicaciones ADD CONSTRAINT FK_nombreDeUsuario3 FOREIGN KEY (nombreDeUsuarioVendedor) REFERENCES usuarios(nombreDeUsuario);

--
-- Indices de la tabla ventas
--
ALTER TABLE dbo.ventas ADD CONSTRAINT FK_nombreDeUsuario4 FOREIGN KEY (nombreDeUsuarioComprador) REFERENCES usuarios(nombreDeUsuario);
ALTER TABLE dbo.ventas ADD CONSTRAINT FK_nroPublicacion3 FOREIGN KEY (nroPublicacion) REFERENCES publicaciones(nroPublicacion);

--
-- Indices de la tabla ventas
--
ALTER TABLE dbo.calificaciones ADD CONSTRAINT FK_calif_comprador FOREIGN KEY (comprador) REFERENCES usuarios(nombreDeUsuario);
ALTER TABLE dbo.calificaciones ADD CONSTRAINT FK_calif_vendedor FOREIGN KEY (vendedor) REFERENCES usuarios(nombreDeUsuario);
ALTER TABLE dbo.calificaciones ADD CONSTRAINT FK_calif_venta FOREIGN KEY (venta) REFERENCES ventas(nroVenta);

