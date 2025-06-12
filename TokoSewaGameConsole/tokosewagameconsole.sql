-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Waktu pembuatan: 12 Jun 2025 pada 08.41
-- Versi server: 10.4.32-MariaDB
-- Versi PHP: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `tokosewagameconsole`
--

-- --------------------------------------------------------

--
-- Struktur dari tabel `admin`
--

CREATE TABLE `admin` (
  `id` int(12) NOT NULL,
  `nama` varchar(100) NOT NULL,
  `sandi` varchar(30) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data untuk tabel `admin`
--

INSERT INTO `admin` (`id`, `nama`, `sandi`) VALUES
(1, 'Abdul', '123');

-- --------------------------------------------------------

--
-- Struktur dari tabel `berlangganan`
--

CREATE TABLE `berlangganan` (
  `KTP` varchar(15) NOT NULL,
  `Nama` varchar(100) NOT NULL,
  `status` varchar(20) NOT NULL,
  `tanggal_expired` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data untuk tabel `berlangganan`
--

INSERT INTO `berlangganan` (`KTP`, `Nama`, `status`, `tanggal_expired`) VALUES
('111111111111111', 'tujuh', 'Expired', '2025-05-13'),
('777777777777777', 'Haryono', 'Aktif', '2025-07-13'),
('888888888888888', 'Bayu Umar', 'Aktif', '2025-06-14');

-- --------------------------------------------------------

--
-- Struktur dari tabel `console`
--

CREATE TABLE `console` (
  `id` int(11) NOT NULL,
  `paket` varchar(100) NOT NULL,
  `deskripsi` text DEFAULT NULL,
  `stock` int(11) NOT NULL DEFAULT 0,
  `harga` decimal(10,2) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data untuk tabel `console`
--

INSERT INTO `console` (`id`, `paket`, `deskripsi`, `stock`, `harga`) VALUES
(1, 'PS5++ ', 'PS5 Console Disc, Free 5x BlueRay Disc Request, 2 stick wireless , 25 Games Ready', 8, 60000.00),
(2, 'XBox Series X', 'XBox Series X, Free 5x BlueRay Disc Request, 2 stick wireless , 25 Games Ready', 55, 54000.00),
(3, 'PS5 Basic', 'PS5 Console Digital, 2 stick wired , 20 Games Ready', 15, 45000.00),
(4, 'PS4++', 'PS4 , Free 5x BlueRay Disc Request, 2 stick wireless , 15 Games Ready', 21, 39000.00),
(5, 'XBox Series S', 'XBox Series S, 2 stick wireless , 20 Games Ready', 10, 33000.00),
(6, 'PS4 Basic', 'PS4, 2 stick wired , 15 Games Ready.', 15, 28000.00),
(7, 'PS3+ ', 'PS3, Free 2x BlueRay Disc Request , 2 stick wireless , 10 Games Ready.', 13, 24000.00),
(8, 'PS3 Basic', 'PS3, 2 stick wired , 10 Games Ready', 77, 20000.00),
(9, 'PS2 Retro', 'PS2, 2 stick wired , 8 Games Ready', 15, 15000.00),
(10, 'PS1 Purba', 'PS1 , 2 stick wired , 5 Game Disc Request', 5, 10000.00);

-- --------------------------------------------------------

--
-- Struktur dari tabel `diskon`
--

CREATE TABLE `diskon` (
  `kode_unik` varchar(12) NOT NULL,
  `persen` int(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data untuk tabel `diskon`
--

INSERT INTO `diskon` (`kode_unik`, `persen`) VALUES
('12345678', 30);

-- --------------------------------------------------------

--
-- Struktur dari tabel `pembayaran`
--

CREATE TABLE `pembayaran` (
  `id` int(12) NOT NULL,
  `fk_admin` int(12) NOT NULL,
  `ktp` varchar(15) NOT NULL,
  `nama_pelanggan` varchar(100) NOT NULL,
  `fk_console` int(12) NOT NULL,
  `tanggal_pembayaran` date NOT NULL,
  `lama_peminjaman` int(6) NOT NULL,
  `tanggal_sudah_kembali` date DEFAULT NULL,
  `total_harga` decimal(20,0) NOT NULL,
  `status_console` varchar(25) NOT NULL,
  `tanggal_expired` date NOT NULL,
  `Catatan` varchar(200) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data untuk tabel `pembayaran`
--

INSERT INTO `pembayaran` (`id`, `fk_admin`, `ktp`, `nama_pelanggan`, `fk_console`, `tanggal_pembayaran`, `lama_peminjaman`, `tanggal_sudah_kembali`, `total_harga`, `status_console`, `tanggal_expired`, `Catatan`) VALUES
(2, 1, '43423423', 'sdfasdf', 1, '2025-06-04', 7, '2025-06-11', 45245, 'Dikembalikan', '2025-06-11', NULL),
(3, 1, '2324134123', 'Budi', 3, '2025-06-11', 5, '2025-06-11', 50000, 'Dikembalikan', '2025-06-16', NULL),
(5, 1, '12312312312', 'Habibi', 3, '2025-06-11', 7, NULL, 315000, 'Dipinjam', '2025-06-18', 'tambah 1 controller'),
(6, 1, '1231231', 'Naruto', 1, '2025-06-11', 7, NULL, 420000, 'Dipinjam', '2025-06-18', 'tambah 2 stick'),
(7, 1, '222222222', 'Sakure', 10, '2025-06-11', 2, NULL, 20000, 'Dipinjam', '2025-06-13', ''),
(8, 1, '99999999999', 'Sasuka', 1, '2025-06-11', 99, '2025-06-11', 5940000, 'Dikembalikan', '2025-09-18', ''),
(9, 1, '777777777777777', 'Haryono', 1, '2025-06-12', 7, NULL, 294000, 'Dipinjam', '2025-06-19', 'Tambahkan Stick'),
(10, 1, '888888888888888', 'Budi', 3, '2025-06-12', 1, NULL, 31500, 'Dipinjam', '2025-06-13', ''),
(11, 1, '222222222222222', 'Aji', 1, '2025-06-12', 4, NULL, 240000, 'Dipinjam', '2025-06-16', ''),
(12, 1, '666666666666666', 'Hayu', 3, '2025-06-12', 1, NULL, 45000, 'Dipinjam', '2025-06-13', '');

--
-- Indexes for dumped tables
--

--
-- Indeks untuk tabel `admin`
--
ALTER TABLE `admin`
  ADD PRIMARY KEY (`id`);

--
-- Indeks untuk tabel `berlangganan`
--
ALTER TABLE `berlangganan`
  ADD PRIMARY KEY (`KTP`);

--
-- Indeks untuk tabel `console`
--
ALTER TABLE `console`
  ADD PRIMARY KEY (`id`);

--
-- Indeks untuk tabel `diskon`
--
ALTER TABLE `diskon`
  ADD PRIMARY KEY (`kode_unik`);

--
-- Indeks untuk tabel `pembayaran`
--
ALTER TABLE `pembayaran`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT untuk tabel yang dibuang
--

--
-- AUTO_INCREMENT untuk tabel `admin`
--
ALTER TABLE `admin`
  MODIFY `id` int(12) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT untuk tabel `console`
--
ALTER TABLE `console`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;

--
-- AUTO_INCREMENT untuk tabel `pembayaran`
--
ALTER TABLE `pembayaran`
  MODIFY `id` int(12) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=13;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
