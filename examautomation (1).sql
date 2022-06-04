-- phpMyAdmin SQL Dump
-- version 5.1.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Apr 28, 2022 at 04:57 PM
-- Server version: 10.4.22-MariaDB
-- PHP Version: 7.4.26

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `examautomation`
--

-- --------------------------------------------------------

--
-- Table structure for table `course`
--

CREATE TABLE `course` (
  `Course_ID` varchar(10) NOT NULL,
  `Course_Name` varchar(100) NOT NULL,
  `Course_Branch` varchar(100) NOT NULL,
  `Course_semester` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `course`
--

INSERT INTO `course` (`Course_ID`, `Course_Name`, `Course_Branch`, `Course_semester`) VALUES
('Cs202', 'System Software', 'CS', '4');

-- --------------------------------------------------------

--
-- Table structure for table `examdata`
--

CREATE TABLE `examdata` (
  `Exam_Date` varchar(255) NOT NULL,
  `Student_ID` varchar(255) NOT NULL,
  `Course_ID` varchar(255) NOT NULL,
  `Hall_ID` varchar(255) NOT NULL,
  `Teacher_ID` varchar(255) NOT NULL,
  `Exam_ID` int(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Table structure for table `hall`
--

CREATE TABLE `hall` (
  `Hall_ID` varchar(10) NOT NULL,
  `Hall_Capacity` int(200) NOT NULL,
  `Hall_Availability` varchar(10) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `hall`
--

INSERT INTO `hall` (`Hall_ID`, `Hall_Capacity`, `Hall_Availability`) VALUES
('9201', 50, 'false'),
('9202', 50, 'true');

-- --------------------------------------------------------

--
-- Table structure for table `setexam`
--

CREATE TABLE `setexam` (
  `Exam_ID` int(20) NOT NULL,
  `Exam_Date` varchar(255) NOT NULL,
  `Exam_TimeInterval` varchar(50) NOT NULL,
  `Exam_Branch` varchar(60) NOT NULL,
  `Exam_Semester` varchar(10) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Table structure for table `student`
--

CREATE TABLE `student` (
  `Student_ID` varchar(10) NOT NULL,
  `Student_FirstName` varchar(30) NOT NULL,
  `Student_LastName` varchar(50) NOT NULL,
  `Student_Branch` varchar(50) NOT NULL,
  `Student_semester` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `student`
--

INSERT INTO `student` (`Student_ID`, `Student_FirstName`, `Student_LastName`, `Student_Branch`, `Student_semester`) VALUES
('202051036', 'Arunava', 'Barua', 'IT', 4),
('202051038', 'Aryan', 'Gupta', 'CS', 4),
('202051087', 'vishal', 'hotwani', 'CS', 4);

-- --------------------------------------------------------

--
-- Table structure for table `teacher`
--

CREATE TABLE `teacher` (
  `Teacher_ID` varchar(10) NOT NULL,
  `Teacher_FirstName` varchar(50) NOT NULL,
  `Teacher_LastName` varchar(50) NOT NULL,
  `Teacher_Availability` varchar(5) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `teacher`
--

INSERT INTO `teacher` (`Teacher_ID`, `Teacher_FirstName`, `Teacher_LastName`, `Teacher_Availability`) VALUES
('2201', 'Amit', 'Dwivedi', 'true');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `course`
--
ALTER TABLE `course`
  ADD PRIMARY KEY (`Course_ID`);

--
-- Indexes for table `hall`
--
ALTER TABLE `hall`
  ADD PRIMARY KEY (`Hall_ID`);

--
-- Indexes for table `setexam`
--
ALTER TABLE `setexam`
  ADD PRIMARY KEY (`Exam_ID`);

--
-- Indexes for table `student`
--
ALTER TABLE `student`
  ADD PRIMARY KEY (`Student_ID`);

--
-- Indexes for table `teacher`
--
ALTER TABLE `teacher`
  ADD PRIMARY KEY (`Teacher_ID`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
