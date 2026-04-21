class DrawingDto {
  final String drawingId;
  final String imageURL;
  final String emotionClass;
  final String emotionalInterpretation;
  final String createdAt;
  final List<String> doctorIds;

  DrawingDto({
    required this.drawingId,
    required this.imageURL,
    required this.emotionClass,
    required this.emotionalInterpretation,
    required this.createdAt,
    required this.doctorIds,
  });

  factory DrawingDto.fromJson(Map<String, dynamic> json) {
    return DrawingDto(
      drawingId: (json['drawingId'] ?? '').toString(),
      imageURL: (json['imageURL'] ?? '').toString(),
      emotionClass: (json['emotionClass'] ?? '').toString(),
      emotionalInterpretation: (json['emotionalInterpretation'] ?? '')
          .toString(),
      createdAt: (json['createdAt'] ?? '').toString(),
      doctorIds: (json['doctorIds'] as List? ?? [])
          .map((e) => e.toString())
          .toList(),
    );
  }

  Map<String, dynamic> toJson() {
    return {
      'drawingId': drawingId,
      'imageURL': imageURL,
      'emotionClass': emotionClass,
      'emotionalInterpretation': emotionalInterpretation,
      'createdAt': createdAt,
      'doctorIds': doctorIds,
    };
  }
}
