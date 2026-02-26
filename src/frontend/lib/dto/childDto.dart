class ChildDto {
  final String childID;
  final String name;
  final String dateOfBirth;
  final String gender;

  ChildDto({
    required this.childID,
    required this.name,
    required this.dateOfBirth,
    required this.gender,
  });

  factory ChildDto.fromJson(Map<String, dynamic> json) {
    return ChildDto(
      childID: json['childID'] ?? "",
      name: json['name'] ?? "",
      dateOfBirth: json['dateOfBirth'] ?? "",
      gender: json['gender'] ?? "",
    );
  }

  Map<String, dynamic> toJson() {
    return {
      'childID': childID,
      'name': name,
      'dateOfBirth': dateOfBirth,
      'gender': gender,
    };
  }
}
